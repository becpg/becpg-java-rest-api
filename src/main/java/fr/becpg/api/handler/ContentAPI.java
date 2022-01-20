package fr.becpg.api.handler;

import java.io.IOException;

import fr.becpg.api.model.RemoteNodeInfo;

public interface ContentAPI {

	String getOrCreateSharedUrl(RemoteNodeInfo remoteNodeInfo) throws IOException;
	
}
