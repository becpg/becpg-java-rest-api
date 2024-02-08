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
	 * Returns the list of the entities that are in the channel
	 *
	 * @param channelId a {@link java.lang.String} object
	 * @return a {@link java.util.List} object
	 */
	List<RemoteEntityRef> list(String channelId);

	/**
	 * Returns the list of the entities that are in the channel with a max result. The return values will be retrieved with the attributes filled
	 * in parameter
	 *
	 * @param channelId a {@link java.lang.String} object
	 * @param attributes a {@link java.util.List} object
	 * @param maxResults a int
	 * @return a {@link java.util.List} object
	 */
	List<RemoteEntityRef> list(String channelId, List<String> attributes, int maxResults);

	/**
	 * Returns the list of the publication channel that are present in the PLM. The return values will be retrieved with the attributes filled
	 * in parameter
	 *
	 * @param channelId a {@link java.lang.String} object
	 * @param attributes a {@link java.util.List} object
	 * @param maxResults a int
	 * @return a {@link java.util.List} object
	 */
	List<RemoteEntityRef> list(List<String> attributes);

	/**
	 * Returns the channel with the id channelId
	 *
	 * @param channelId a {@link java.lang.String} object
	 * @return a {@link fr.becpg.api.model.RemoteEntity} object
	 */
	RemoteEntity get(String channelId);

}
