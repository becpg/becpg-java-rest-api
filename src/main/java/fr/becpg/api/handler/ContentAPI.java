package fr.becpg.api.handler;

import java.io.IOException;
import java.nio.file.Path;

import fr.becpg.api.model.RemoteNodeInfo;

public interface ContentAPI {

	String getOrCreateSharedUrl(RemoteNodeInfo remoteNodeInfo) throws IOException;

	void writeContent(RemoteNodeInfo remoteNodeInfo, Path destFile) throws IOException;

	
}
