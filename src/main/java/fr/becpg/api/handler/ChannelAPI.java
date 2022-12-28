package fr.becpg.api.handler;

import java.util.List;

import fr.becpg.api.model.RemoteEntity;
import fr.becpg.api.model.RemoteEntityRef;

/**
 * <p>ChannelAPI interface.</p>
 *
 * @author matthieu
 * @version $Id: $Id
 */
public interface ChannelAPI {

	/**
	 * <p>list.</p>
	 *
	 * @param channelId a {@link java.lang.String} object
	 * @return a {@link java.util.List} object
	 */
	List<RemoteEntityRef> list(String channelId);
	/**
	 * <p>list.</p>
	 *
	 * @param channelId a {@link java.lang.String} object
	 * @param attributes a {@link java.util.List} object
	 * @param maxResults a int
	 * @return a {@link java.util.List} object
	 */
	List<RemoteEntityRef> list(String channelId, List<String> attributes,  int maxResults);
	/**
	 * <p>get.</p>
	 *
	 * @param channelId a {@link java.lang.String} object
	 * @return a {@link fr.becpg.api.model.RemoteEntity} object
	 */
	RemoteEntity get(String channelId);
	
}
