package fr.becpg.api.handler;

import java.io.File;
import java.io.IOException;

import fr.becpg.api.model.RemoteNodeInfo;

public interface ContentAPI {

	String getOrCreateSharedUrl(RemoteNodeInfo remoteNodeInfo) throws IOException;

	void writeContent(RemoteNodeInfo remoteNodeInfo, File destFile) throws IOException;
	
}
