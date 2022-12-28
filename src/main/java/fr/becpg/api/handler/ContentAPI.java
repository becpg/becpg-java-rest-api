package fr.becpg.api.handler;

import java.io.IOException;
import java.nio.file.Path;

import fr.becpg.api.model.RemoteNodeInfo;

/**
 * <p>ContentAPI interface.</p>
 *
 * @author matthieu
 * @version $Id: $Id
 */
public interface ContentAPI {

	/**
	 * <p>getOrCreateSharedUrl.</p>
	 *
	 * @param remoteNodeInfo a {@link fr.becpg.api.model.RemoteNodeInfo} object
	 * @return a {@link java.lang.String} object
	 * @throws java.io.IOException if any.
	 */
	String getOrCreateSharedUrl(RemoteNodeInfo remoteNodeInfo) throws IOException;

	/**
	 * <p>writeContent.</p>
	 *
	 * @param remoteNodeInfo a {@link fr.becpg.api.model.RemoteNodeInfo} object
	 * @param destFile a {@link java.nio.file.Path} object
	 * @throws java.io.IOException if any.
	 */
	void writeContent(RemoteNodeInfo remoteNodeInfo, Path destFile) throws IOException;

	
}
