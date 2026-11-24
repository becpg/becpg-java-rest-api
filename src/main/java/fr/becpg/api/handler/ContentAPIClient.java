package fr.becpg.api.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import fr.becpg.api.model.RemoteAPIError;
import fr.becpg.api.model.RemoteAPIError.RemoteAPIErrorStatus;
import fr.becpg.api.model.RemoteAPIException;
import fr.becpg.api.model.RemoteDocumentUpload;
import fr.becpg.api.model.RemoteNodeInfo;
import fr.becpg.api.model.RemoteUploadResult;
import fr.becpg.api.model.RemoteVersionUpload;
import fr.becpg.api.security.EnableAuthConfiguration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * <p>ContentAPIClient class.</p>
 *
 * @version $Id: $Id
 * @author matthieu
 */
@Component
@EnableAuthConfiguration
public class ContentAPIClient extends AbstractAPIClient implements ContentAPI {

	private static final String PARAM_SHARE = "share";
	private static final String REMOTE_CONTENT_URL = "/entity/content";
	private static final String UPLOAD_PATH = "/alfresco/service/api/upload";
	private static final String PART_FILEDATA = "filedata";
	private static final String PART_FILENAME = "filename";
	private static final String PART_DESTINATION = "destination";
	private static final String PART_UPDATE_NODE_REF = "updatenoderef";
	private static final String PART_MAJOR_VERSION = "majorversion";
	private static final String PART_DESCRIPTION = "description";
	private static final String STATUS_UNAUTHORIZED = "401";
	private static final String STATUS_BAD_GATEWAY = "502";
	private static final String CONSUMED_STREAM_MARKER = "already been read";
	private static final String MSG_EMPTY_ANSWER = "the upload webscript answered without any content";
	private static final String MSG_STREAM_CONSUMED = "the upload was refused with 401 and cannot be retried: its content stream was already read";

	@JsonIgnoreProperties(ignoreUnknown = true)
	private record UploadAnswer(String nodeRef, String fileName) {
	}

	/** {@inheritDoc} */
	@Override
	public String getOrCreateSharedUrl(RemoteNodeInfo remoteNodeInfo) throws IOException {
		String sharedId = remoteNodeInfo.getStringProp("qshare:sharedId");

		if (sharedId == null) {
			DataBuffer dataBuffer = webClient().get()
					.uri(uriBuilder -> uriBuilder.path(REMOTE_CONTENT_URL).queryParam(PARAM_NODEREF, buildNodeRefParam(remoteNodeInfo.getId()))
							.queryParam(PARAM_SHARE, true).build())
					.retrieve()
					.onStatus(HttpStatusCode::isError,
							response -> response.bodyToMono(RemoteAPIError.class).flatMap(error -> Mono.error(new RemoteAPIException(error))))
					.bodyToMono(DataBuffer.class).block();

			if (dataBuffer != null) {
				try (InputStream in = dataBuffer.asInputStream(true)) {
					sharedId = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));

				}
			}

		}
		
		String contentServiceUrl = apiConfiguration.getContentServiceUrl().replaceAll("/+$", "");
		return contentServiceUrl + "/alfresco/service/api/internal/shared/node/" + sharedId + "/content";
	}

	/** {@inheritDoc} */
	@Override
	public void writeContent(RemoteNodeInfo remoteNodeInfo, Path filePath) throws IOException {

		Flux<DataBuffer> dataBuffer = webClient().get()
				.uri(uriBuilder -> uriBuilder.path(REMOTE_CONTENT_URL).queryParam(PARAM_NODEREF, buildNodeRefParam(remoteNodeInfo.getId())).build())
				.retrieve()
				.onStatus(HttpStatusCode::isError,
						response -> response.bodyToMono(RemoteAPIError.class).flatMap(error -> Mono.error(new RemoteAPIException(error))))
				.bodyToFlux(DataBuffer.class);

		DataBufferUtils.write(dataBuffer, filePath, StandardOpenOption.CREATE).block();

	}

	/** {@inheritDoc} */
	@Override
	public InputStream getContent(RemoteNodeInfo remoteNodeInfo) throws IOException {

		DataBuffer dataBuffer = webClient().get()
				.uri(uriBuilder -> uriBuilder.path(REMOTE_CONTENT_URL).queryParam(PARAM_NODEREF, buildNodeRefParam(remoteNodeInfo.getId())).build())
				.retrieve()
				.onStatus(HttpStatusCode::isError,
						response -> response.bodyToMono(RemoteAPIError.class).flatMap(error -> Mono.error(new RemoteAPIException(error))))
				.bodyToMono(DataBuffer.class).block();
		if (dataBuffer != null) {
			return dataBuffer.asInputStream(true);
		}
		return null;

	}

	/** {@inheritDoc} */
	@Override
	public RemoteUploadResult uploadDocument(RemoteDocumentUpload upload, InputStream content) {
		Objects.requireNonNull(upload, "upload is required");
		Objects.requireNonNull(upload.folderId(), "folderId is required");
		MultipartBodyBuilder body = newUploadBody(upload.fileName(), content);
		body.part(PART_DESTINATION, buildNodeRefParam(upload.folderId()));
		return postUpload(body);
	}

	/** {@inheritDoc} */
	@Override
	public RemoteUploadResult uploadDocumentVersion(RemoteVersionUpload upload, InputStream content) {
		Objects.requireNonNull(upload, "upload is required");
		Objects.requireNonNull(upload.documentId(), "documentId is required");
		MultipartBodyBuilder body = newUploadBody(upload.fileName(), content);
		body.part(PART_UPDATE_NODE_REF, buildNodeRefParam(upload.documentId()));
		body.part(PART_MAJOR_VERSION, String.valueOf(upload.majorVersion()));
		if (upload.comment() != null) {
			body.part(PART_DESCRIPTION, upload.comment());
		}
		return postUpload(body);
	}

	private MultipartBodyBuilder newUploadBody(String fileName, InputStream content) {
		Objects.requireNonNull(fileName, "fileName is required");
		Objects.requireNonNull(content, "content is required");
		MultipartBodyBuilder body = new MultipartBodyBuilder();
		// The file name is mandatory: without it the webscript ignores the part
		body.part(PART_FILEDATA, new InputStreamResource(content)).filename(fileName);
		body.part(PART_FILENAME, fileName);
		return body;
	}

	private RemoteUploadResult postUpload(MultipartBodyBuilder body) {
		// The upload webscript is not under the remote API base path of the web client
		UploadAnswer answer = webClient().post().uri(uriBuilder -> uriBuilder.replacePath(UPLOAD_PATH).build())
				.body(BodyInserters.fromMultipartData(body.build())).retrieve()
				.onStatus(HttpStatusCode::isError, response -> handleErrorResponse(response)).bodyToMono(UploadAnswer.class)
				.switchIfEmpty(Mono.error(() -> buildRemoteError(STATUS_BAD_GATEWAY, MSG_EMPTY_ANSWER)))
				.onErrorMap(this::isConsumedStream, error -> buildRemoteError(STATUS_UNAUTHORIZED, MSG_STREAM_CONSUMED)).block();
		return new RemoteUploadResult(answer.nodeRef(), answer.fileName());
	}

	private boolean isConsumedStream(Throwable error) {
		for (Throwable cause = error; cause != null; cause = cause.getCause()) {
			if (cause instanceof IllegalStateException && cause.getMessage() != null && cause.getMessage().contains(CONSUMED_STREAM_MARKER)) {
				return true;
			}
		}
		return false;
	}

}
