package fr.becpg.api.handler;

import java.util.List;
import java.util.Map;

import org.springframework.lang.NonNull;

import fr.becpg.api.model.RemoteEntity;
import fr.becpg.api.model.RemoteEntityRef;
import fr.becpg.api.model.RemoteAPIResponse;

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
	List<RemoteEntityRef> list(@NonNull String channelId);

	/**
	 * Returns the list of the entities that are in the channel with a max result. The return values will be retrieved with the attributes filled
	 * in parameter
	 *
	 * @param channelId a {@link java.lang.String} object
	 * @param attributes a {@link java.util.List} object
	 * @param maxResults a int
	 * @return a {@link java.util.List} object
	 */
	List<RemoteEntityRef> list(@NonNull String channelId, List<String> attributes, int maxResults);

	/**
	 * Returns the channel with the id channelId
	 *
	 * @param channelId a {@link java.lang.String} object
	 * @return a {@link fr.becpg.api.model.RemoteEntity} object
	 */
	RemoteEntity get(@NonNull String channelId);

	/**
	 * Starts a batch for the specified channel.
	 *
	 * @param channelId the channel ID
	 * @param batchId the batch ID
	 * @return the response of the remote API
	 */
	RemoteAPIResponse batchStart(@NonNull String channelId, @NonNull String batchId);

	/**
	 * Updates the channel status on an entity within a batch.
	 *
	 * @param channelId the channel ID
	 * @param nodeRef the NodeRef of the entity
	 * @param attributes the attributes to update
	 * @return the response of the remote API
	 */
	RemoteAPIResponse batchAck(@NonNull String channelId, @NonNull String nodeRef, @NonNull Map<String, Object> attributes);

	/**
	 * Ends the batch for the specified channel.
	 *
	 * @param channelId the channel ID
	 * @param attributes the channel attributes to update at the end of the batch
	 * @return the response of the remote API
	 */
	RemoteAPIResponse batchEnd(@NonNull String channelId, @NonNull Map<String, Object> attributes);

}
