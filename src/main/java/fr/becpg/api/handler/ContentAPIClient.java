package fr.becpg.api.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.stream.Collectors;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import fr.becpg.api.model.RemoteAPIError;
import fr.becpg.api.model.RemoteAPIException;
import fr.becpg.api.model.RemoteNodeInfo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * <p>ContentAPIClient class.</p>
 *
 * @version $Id: $Id
 */
@Component
public class ContentAPIClient extends AbstractAPIClient implements ContentAPI {

	private static final String PARAM_SHARE = "share";

	/** {@inheritDoc} */
	@Override
	public String getOrCreateSharedUrl(RemoteNodeInfo remoteNodeInfo) throws IOException {
		String sharedId = remoteNodeInfo.getStringProp("qshare:sharedId");

		if (sharedId == null) {
			DataBuffer dataBuffer = webClient.get()
					.uri(uriBuilder -> uriBuilder.path("/entity/content").queryParam(PARAM_NODEREF, buildNodeRefParam(remoteNodeInfo.getId()))
							.queryParam(PARAM_SHARE, true).build())
					.retrieve()
					.onStatus(HttpStatus::isError,
							response -> response.bodyToMono(RemoteAPIError.class).flatMap(error -> Mono.error(new RemoteAPIException(error))))
					.bodyToMono(DataBuffer.class).block();

			if (dataBuffer != null) {
				try (InputStream in = dataBuffer.asInputStream()) {
					sharedId = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));

				}
			}

		}
		return apiConfiguration.getContentServiceUrl() + "/alfresco/service/api/internal/shared/node/" + sharedId + "/content";
	}

	/** {@inheritDoc} */
	@Override
	public void writeContent(RemoteNodeInfo remoteNodeInfo, Path filePath) throws IOException {

		Flux<DataBuffer> dataBuffer = webClient.get()
				.uri(uriBuilder -> uriBuilder.path("/entity/content").queryParam(PARAM_NODEREF, buildNodeRefParam(remoteNodeInfo.getId())).build())
				.retrieve()
				.onStatus(HttpStatus::isError,
						response -> response.bodyToMono(RemoteAPIError.class).flatMap(error -> Mono.error(new RemoteAPIException(error))))
				.bodyToFlux(DataBuffer.class);

		DataBufferUtils.write(dataBuffer, filePath, StandardOpenOption.CREATE).block();

	}

}
