package fr.becpg.api.model;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>ChannelAPIModel class.</p>
 *
 * @author matthieu
 * @version $Id: $Id
 */
public class ChannelAPIModel {

	private ChannelAPIModel() {
		//Do nothing
	}

	/** Constant <code>TYPE_CHANNEL="bp:pubChannel"</code> */
	public static final String TYPE_CHANNEL = "bp:pubChannel";
	/** Constant <code>TYPE_CHANNEL_LIST="bp:pubChannelList"</code> */
	public static final String TYPE_CHANNEL_LIST = "bp:pubChannelList";

	/** Constant <code>ASSOC_CHANNELLIST_CHANNEL="bp:pubChannelListChannel"</code> */
	public static final String ASSOC_CHANNELLIST_CHANNEL = "bp:pubChannelListChannel";
	/** Constant <code>PROP_CHANNEL_CATALOG_ID="bp:pubChannelCatalogId"</code> */
	public static final String PROP_CHANNEL_CATALOG_ID = "bp:pubChannelCatalogId";
	/** Constant <code>PROP_CHANNELLIST_MODIFIED_DATE="bp:pubChannelListModifiedDate"</code> */
	public static final String PROP_CHANNELLIST_MODIFIED_DATE = "bp:pubChannelListModifiedDate";
	/** Constant <code>PROP_CHANNEL_ID="bp:pubChannelId"</code> */
	public static final String PROP_CHANNEL_ID = "bp:pubChannelId";
	/** Constant <code>PROP_CHANNEL_ACTION="bp:pubChannelAction"</code> */
	public static final String PROP_CHANNEL_ACTION = "bp:pubChannelAction";
	/** Constant <code>PROP_CHANNEL_ACTION="bp:pubChannelAction"</code> */
	public static final String PROP_CHANNEL_LASTDATE = "bp:pubChannelLastDate";
	
	/** Constant <code>PROP_CHANNELLIST_BATCHID="bp:pubChannelListBatchId"</code> */
	public static final String PROP_CHANNELLIST_BATCHID = "bp:pubChannelListBatchId";
	/** Constant <code>PROP_CHANNELLIST_PUBLISHEDDATE="bp:pubChannellListPublishedDate"</code> */
	public static final String PROP_CHANNELLIST_PUBLISHEDDATE = "bp:pubChannellListPublishedDate";
	/** Constant <code>PROP_CHANNELLIST_STATUS="bp:pubChannellListStatus"</code> */
	public static final String PROP_CHANNELLIST_STATUS = "bp:pubChannellListStatus";
	/** Constant <code>PROP_CHANNELLIST_ERROR="bp:pubChannellListError"</code> */
	public static final String PROP_CHANNELLIST_ERROR = "bp:pubChannellListError";
	/** Constant <code>PROP_CHANNELLIST_FORCEPUBLIVCATION="bp:pubChannelListForcePublication"</code> */
	public static final String PROP_CHANNELLIST_FORCEPUBLIVCATION = "bp:pubChannelListForcePublication";
	/** Constant <code>PROP_CHANNEL_CONFIG="bp:pubChannelConfig"</code> */
	public static final String PROP_CHANNEL_CONFIG = "bp:pubChannelConfig";
	/** Constant <code>ASSOC_CHANNEL_CONFIGFILE="bp:pubChannelConfigFile"</code> */
	public static final String ASSOC_CHANNEL_CONFIGFILE = "bp:pubChannelConfigFile";

	/** Constant <code>PROP_CHANNEL_BATCHSTARTTIME="bp:pubChannelBatchStartTime"</code> */
	public static final String PROP_CHANNEL_BATCHSTARTTIME = "bp:pubChannelBatchStartTime";
	/** Constant <code>PROP_CHANNEL_BATCHENDTIME="bp:pubChannelBatchEndTime"</code> */
	public static final String PROP_CHANNEL_BATCHENDTIME = "bp:pubChannelBatchEndTime";
	/** Constant <code>PROP_CHANNEL_BATCHDURATION="bp:pubChannelBatchDuration"</code> */
	public static final String PROP_CHANNEL_BATCHDURATION = "bp:pubChannelBatchDuration";
	/** Constant <code>PROP_CHANNEL_BATCHID="bp:pubChannelBatchId"</code> */
	public static final String PROP_CHANNEL_BATCHID = "bp:pubChannelBatchId";
	/** Constant <code>PROP_CHANNEL_FAILCOUNT="bp:pubChannelFailCount"</code> */
	public static final String PROP_CHANNEL_FAILCOUNT = "bp:pubChannelFailCount";
	/** Constant <code>PROP_CHANNEL_READCOUNT="bp:pubChannelReadCount"</code> */
	public static final String PROP_CHANNEL_READCOUNT = "bp:pubChannelReadCount";
	/** Constant <code>PROP_CHANNEL_ERROR="bp:pubChannelError"</code> */
	public static final String PROP_CHANNEL_ERROR = "bp:pubChannelError";
	/** Constant <code>PROP_CHANNEL_LASTSUCCESSBATCHID="bp:pubChannelLastSuccessBatchId"</code> */
	public static final String PROP_CHANNEL_LASTSUCCESSBATCHID = "bp:pubChannelLastSuccessBatchId";
	/** Constant <code>PROP_CHANNEL_STATUS="bp:pubChannelStatus"</code> */
	public static final String PROP_CHANNEL_STATUS = "bp:pubChannelStatus";

	/** Constant <code>STATUS_COMPLETED="COMPLETED"</code> */
	public static final String STATUS_COMPLETED = "COMPLETED";
	/** Constant <code>STATUS_STARTING="STARTING"</code> */
	public static final String STATUS_STARTING = "STARTING";
	/** Constant <code>STATUS_STARTED="STARTED"</code> */
	public static final String STATUS_STARTED = "STARTED";
	/** Constant <code>STATUS_STOPPING="STOPPING"</code> */
	public static final String STATUS_STOPPING = "STOPPING";
	/** Constant <code>STATUS_STOPPED="STOPPED"</code> */
	public static final String STATUS_STOPPED = "STOPPED";
	/** Constant <code>STATUS_FAILED="FAILED"</code> */
	public static final String STATUS_FAILED = "FAILED";
	/** Constant <code>STATUS_ABANDONED="ABANDONED"</code> */
	public static final String STATUS_ABANDONED = "ABANDONED";
	/** Constant <code>STATUS_UNKNOWN="UNKNOWN"</code> */
	public static final String STATUS_UNKNOWN = "UNKNOWN";
	
	
	public static final String ACTION_RETRY = "RETRY";
	public static final String ACTION_RESET = "RESET";
	public static final String ACTION_STOP = "STOP";
	
	
	public static final String FIELDS = "bp:pubChannelId,bp:pubChannelAction,bp:pubChannelLastDate,bp:pubChannelConfig,bp:pubChannelConfigFile";

	/**
	 * <p>createChannelEntity.</p>
	 *
	 * @param channelId a {@link java.lang.String} object
	 * @return a {@link fr.becpg.api.model.RemoteEntity} object
	 */
	public static RemoteEntity createChannelEntity(String channelId) {
		RemoteEntity channelEntity = new RemoteEntity();
		channelEntity.setType(TYPE_CHANNEL);
		channelEntity.setPath("/app:company_home/cm:System/cm:Characts/bcpg:entityLists/cm:PubChannels");
		Map<String, Object> identifiers = new HashMap<>();
		identifiers.put(PROP_CHANNEL_ID, channelId);
		channelEntity.setOptionalIdentifiers(identifiers);
		return channelEntity;
	}

}
