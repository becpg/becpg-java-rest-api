package fr.becpg.api.handler;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import fr.becpg.api.model.RemoteDocumentUpload;
import fr.becpg.api.model.RemoteNodeInfo;
import fr.becpg.api.model.RemoteUploadResult;
import fr.becpg.api.model.RemoteVersionUpload;

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

	/**
	 * <p>getContent.</p>
	 *
	 * @param remoteNodeInfo a {@link fr.becpg.api.model.RemoteNodeInfo} object
	 * @return a {@link java.io.InputStream} object
	 * @throws java.io.IOException if any.
	 */
	InputStream getContent(RemoteNodeInfo remoteNodeInfo) throws IOException;

	/**
	 * Uploads a new document into a folder, through the Share upload webscript <code>/alfresco/service/api/upload</code>.
	 * <p>
	 * beCPG forces the overwrite: when the folder already holds a versionable file of the same name, the content becomes a new version of that
	 * file instead of being refused.
	 * <p>
	 * The content is streamed, never buffered, and is not closed by this method.
	 *
	 * @param upload the folder, the file name and the optional type of the document
	 * @param content the content of the document
	 * @return the created document
	 * @throws fr.becpg.api.model.RemoteAPIException with the status of the webscript on error
	 */
	RemoteUploadResult uploadDocument(RemoteDocumentUpload upload, InputStream content);

	/**
	 * Uploads a new version of an existing document, through the Share upload webscript <code>/alfresco/service/api/upload</code>.
	 * <p>
	 * The content is streamed, never buffered, and is not closed by this method.
	 *
	 * @param upload the document, the file name, the kind of version and its comment
	 * @param content the content of the new version
	 * @return the updated document
	 * @throws fr.becpg.api.model.RemoteAPIException with the status of the webscript on error
	 */
	RemoteUploadResult uploadDocumentVersion(RemoteVersionUpload upload, InputStream content);

	
}
