package fr.becpg.api.handler;

import java.util.List;

import fr.becpg.api.model.RemoteEntityRef;

public interface ChannelAPI {

	List<RemoteEntityRef> list(String channelId);
	List<RemoteEntityRef> list(String channelId, List<String> attributes,  int maxResults);
	
}
