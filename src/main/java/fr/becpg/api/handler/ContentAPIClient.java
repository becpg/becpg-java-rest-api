package fr.becpg.api.handler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.StandardCopyOption;
import java.util.stream.Collectors;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Component;

import fr.becpg.api.model.RemoteNodeInfo;

/**
*
*  @author matthieu
*  <url>/becpg/remote/entity/content?nodeRef={nodeRef}&share={share}</url>
*  */
@Component
public class ContentAPIClient extends AbstractAPIClient implements ContentAPI {

	private static final String PARAM_SHARE = "share";

	@Override
	public String getOrCreateSharedUrl(RemoteNodeInfo remoteNodeInfo) throws IOException {
		String sharedId = remoteNodeInfo.getStringProp("qshare:sharedId");

		if (sharedId == null) {
			DataBuffer dataBuffer = webClient.get().uri(uriBuilder -> uriBuilder.path("/entity/content")
					.queryParam(PARAM_NODEREF, buildNodeRefParam(remoteNodeInfo.getId())).queryParam(PARAM_SHARE, true).build()).retrieve()
					.bodyToMono(DataBuffer.class).block();

			if (dataBuffer != null) {
				try (InputStream in = dataBuffer.asInputStream()) {
					sharedId = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));

				}
			}

		}
		return apiConfiguration.getContentServiceUrl() + "/alfresco/service/api/internal/shared/node/" + sharedId + "/content";
	}

	@Override
	public void writeContent(RemoteNodeInfo remoteNodeInfo, File destFile) throws IOException {

		DataBuffer dataBuffer = webClient.get()
				.uri(uriBuilder -> uriBuilder.path("/entity/content").queryParam(PARAM_NODEREF, buildNodeRefParam(remoteNodeInfo.getId())).build())
				.retrieve().bodyToMono(DataBuffer.class).block();

		if (dataBuffer != null) {
			try (InputStream in = dataBuffer.asInputStream()) {
				 java.nio.file.Files.copy(in, destFile.toPath() ,StandardCopyOption.REPLACE_EXISTING);
				

			}
		}

	}
}
