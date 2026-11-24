package fr.becpg.api.handler;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import fr.becpg.api.model.RemoteAuditPage;
import fr.becpg.api.model.RemoteAuditQuery;
import fr.becpg.api.security.EnableAuthConfiguration;
import reactor.core.publisher.Mono;

/**
 * Calls the <code>/becpg/stats/{type}</code> webscript: request, answer and error synthesis.
 */
@Component
@EnableAuthConfiguration
public class AuditAPIClient extends AbstractAPIClient implements AuditAPI {

	private static final String STATISTICS_PATH = "/alfresco/service/becpg/stats/{type}";

	private static final String TYPE_VARIABLE = "type";

	private static final String FILTER_PARAM = "filter";

	private static final String FROM_DATE_PARAM = "fromDate";

	private static final String TO_DATE_PARAM = "toDate";

	private static final String START_AFTER_ID_PARAM = "startAfterId";

	private static final String DB_ASC_PARAM = "dbAsc";

	private static final String STATUS_BAD_GATEWAY = "502";

	private static final Pattern PLAIN_NAME = Pattern.compile("[A-Za-z_]+");

	private static final String MSG_INVALID_TYPE = "type must be a plain name made of letters and underscores";

	private static final String MSG_EMPTY_ANSWER = "the audit webscript answered without any content";

	@JsonIgnoreProperties(ignoreUnknown = true)
	private record AuditAnswer(List<Map<String, Object>> statistics, Long nextStartAfterId, Boolean scanInterrupted) {
	}

	@Override
	public RemoteAuditPage listEntries(RemoteAuditQuery query) {
		Objects.requireNonNull(query, "query is mandatory");
		Objects.requireNonNull(query.type(), "type is mandatory");
		if (!PLAIN_NAME.matcher(query.type()).matches()) {
			throw new IllegalArgumentException(MSG_INVALID_TYPE);
		}

		// The webscript is not under the remote API base path of the web client
		AuditAnswer answer = webClient().get().uri(uriBuilder -> buildUri(uriBuilder, query)).accept(MediaType.APPLICATION_JSON).retrieve()
				.onStatus(HttpStatusCode::isError, this::handleErrorResponse).bodyToMono(AuditAnswer.class)
				.switchIfEmpty(Mono.error(() -> buildRemoteError(STATUS_BAD_GATEWAY, MSG_EMPTY_ANSWER))).block();
		List<Map<String, Object>> entries = answer.statistics() != null ? answer.statistics() : List.of();
		return new RemoteAuditPage(entries, answer.nextStartAfterId(), answer.scanInterrupted());
	}

	private URI buildUri(UriBuilder uriBuilder, RemoteAuditQuery query) {
		Map<String, Object> variables = new LinkedHashMap<>();
		variables.put(TYPE_VARIABLE, query.type());
		UriBuilder builder = uriBuilder.replacePath(STATISTICS_PATH);
		if (query.filters() != null) {
			for (String filter : query.filters()) {
				addQueryParam(builder, variables, FILTER_PARAM, filter);
			}
		}
		addQueryParam(builder, variables, FROM_DATE_PARAM, query.fromDate());
		addQueryParam(builder, variables, TO_DATE_PARAM, query.toDate());
		addQueryParam(builder, variables, PARAM_MAX_RESULTS, query.maxResults());
		addQueryParam(builder, variables, START_AFTER_ID_PARAM, query.startAfterId());
		addQueryParam(builder, variables, DB_ASC_PARAM, !query.newestFirst());
		return builder.build(variables);
	}

	private void addQueryParam(UriBuilder builder, Map<String, Object> variables, String name, Object value) {
		if (value == null) {
			return;
		}
		String variableName = name + variables.size();
		variables.put(variableName, value);
		builder.queryParam(name, "{" + variableName + "}");
	}

}
