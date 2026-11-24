package fr.becpg.api.handler;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import fr.becpg.api.model.RemoteNodeActionResult;
import fr.becpg.api.model.RemoteNodeRelocation;
import fr.becpg.api.security.EnableAuthConfiguration;
import reactor.core.publisher.Mono;

/**
 * Calls the Share document library actions <code>copy-to</code> and <code>move-to</code>: request, answer and error synthesis.
 * <p>
 * Those webscripts answer 200 with <code>overallSuccess</code> false and no reason when they refuse an action.
 */
@Component
@EnableAuthConfiguration
class DoclibActionClient extends AbstractAPIClient {

	private enum DoclibAction {
		COPY("copy-to", "copy"), MOVE("move-to", "move");

		private final String pathSegment;

		private final String verb;

		DoclibAction(String pathSegment, String verb) {
			this.pathSegment = pathSegment;
			this.verb = verb;
		}
	}

	private static final String DOCLIB_ACTION_PATH = "/alfresco/service/slingshot/doclib/action/{action}/node/workspace/SpacesStore/{uuid}";

	private static final String RELOCATION_REQUIRED = "relocation is required";

	private static final String BODY_NODE_REFS = "nodeRefs";

	private static final String BODY_PARENT_ID = "parentId";

	private static final String STATUS_BAD_REQUEST = "400";

	private static final String STATUS_CONFLICT = "409";

	private static final String STATUS_UNPROCESSABLE = "422";

	private static final String WORKSPACE_PROTOCOL = "workspace";

	private static final String SPACES_STORE = "SpacesStore";

	private static final Pattern NODE_REF_PATTERN = Pattern.compile("^([^:/]+)://([^/]+)/(.*)$");

	private static final Pattern NODE_ID_PATTERN = Pattern.compile("^[A-Za-z0-9_-]+$");

	private static final String MSG_BAD_DESTINATION = "the destination folder %s is not a node of the workspace://SpacesStore store";

	private static final String MSG_NAME_CLASH = "a node with this name already exists in the target folder";

	private static final String MSG_REFUSED = "beCPG refused the %s of %s into %s without giving a reason";

	private static final String MSG_EMPTY_ANSWER = "the repository answered the %s of %s without any content";

	@JsonIgnoreProperties(ignoreUnknown = true)
	private record DoclibActionAnswer(Boolean overallSuccess, List<DoclibActionResult> results) {
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	private record DoclibActionResult(String id, String nodeRef, Boolean success, Boolean fileExist) {
	}

	Mono<RemoteNodeActionResult> copy(RemoteNodeRelocation relocation) {
		return run(DoclibAction.COPY, relocation);
	}

	Mono<RemoteNodeActionResult> move(RemoteNodeRelocation relocation) {
		Objects.requireNonNull(relocation, RELOCATION_REQUIRED);
		Objects.requireNonNull(relocation.parentId(), "parentId is required for a move");
		return run(DoclibAction.MOVE, relocation);
	}

	private Mono<RemoteNodeActionResult> run(DoclibAction action, RemoteNodeRelocation relocation) {
		Objects.requireNonNull(relocation, RELOCATION_REQUIRED);
		Objects.requireNonNull(relocation.id(), "id is required");
		Objects.requireNonNull(relocation.destFolderId(), "destFolderId is required");

		String nodeRef = buildNodeRefParam(relocation.id());
		String destNodeRef = buildNodeRefParam(relocation.destFolderId());
		Matcher destination = NODE_REF_PATTERN.matcher(destNodeRef);
		if (!isWorkspaceNode(destination)) {
			return Mono.error(buildRemoteError(STATUS_BAD_REQUEST, String.format(MSG_BAD_DESTINATION, destNodeRef)));
		}
		Map<String, Object> body = buildBody(nodeRef, relocation);

		// The doclib webscripts are not under the remote API base path of the web client
		return webClient().post()
				.uri(uriBuilder -> uriBuilder.replacePath(DOCLIB_ACTION_PATH).build(action.pathSegment, destination.group(3)))
				.contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(body)).accept(MediaType.APPLICATION_JSON).retrieve()
				.onStatus(HttpStatusCode::isError, response -> handleErrorResponse(response)).bodyToMono(DoclibActionAnswer.class)
				.switchIfEmpty(Mono.error(() -> buildRemoteError(STATUS_UNPROCESSABLE, String.format(MSG_EMPTY_ANSWER, action.verb, nodeRef))))
				.flatMap(answer -> toNodeActionResult(answer, action, nodeRef, destNodeRef));
	}

	private Map<String, Object> buildBody(String nodeRef, RemoteNodeRelocation relocation) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put(BODY_NODE_REFS, List.of(nodeRef));
		if (relocation.parentId() != null) {
			body.put(BODY_PARENT_ID, buildNodeRefParam(relocation.parentId()));
		}
		return body;
	}

	private boolean isWorkspaceNode(Matcher destination) {
		return destination.matches() && WORKSPACE_PROTOCOL.equals(destination.group(1)) && SPACES_STORE.equals(destination.group(2))
				&& NODE_ID_PATTERN.matcher(destination.group(3)).matches();
	}

	private Mono<RemoteNodeActionResult> toNodeActionResult(DoclibActionAnswer answer, DoclibAction action, String nodeRef,
			String destNodeRef) {
		DoclibActionResult first = answer.results() != null && !answer.results().isEmpty() ? answer.results().get(0) : null;
		if (Boolean.TRUE.equals(answer.overallSuccess()) && first != null) {
			return Mono.just(new RemoteNodeActionResult(first.nodeRef(), first.id()));
		}

		if (first != null && Boolean.TRUE.equals(first.fileExist())) {
			return Mono.error(buildRemoteError(STATUS_CONFLICT, MSG_NAME_CLASH));
		}
		return Mono.error(buildRemoteError(STATUS_UNPROCESSABLE, String.format(MSG_REFUSED, action.verb, nodeRef, destNodeRef)));
	}

}
