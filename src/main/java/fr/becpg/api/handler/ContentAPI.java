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
	 * file instead of being refused. A same-name file that is not versionable is not refused either: the new document is silently renamed
	 * <code>name-1.ext</code>, so the <code>fileName</code> of the result is the only hint.
	 * <p>
	 * The content is streamed, never buffered: it is read once, to its end, and closed by the client as soon as it is read (closing it again
	 * is harmless). A request that is sent again, such as the retry of the Basic authentication after an expired ticket, cannot replay it and
	 * fails with a 401 {@link fr.becpg.api.model.RemoteAPIException}. A failure while reading the content is rethrown as a
	 * {@link org.springframework.web.reactive.function.client.WebClientRequestException} whose cause chain holds the original exception.
	 *
	 * @param upload the folder and the file name of the document
	 * @param content the content of the document
	 * @return the created document
	 * @throws fr.becpg.api.model.RemoteAPIException with the status of the webscript on error
	 */
	RemoteUploadResult uploadDocument(RemoteDocumentUpload upload, InputStream content);

	/**
	 * Uploads a new version of an existing document, through the Share upload webscript <code>/alfresco/service/api/upload</code>.
	 * <p>
	 * The content is streamed, never buffered: it is read once, to its end, and closed by the client as soon as it is read (closing it again
	 * is harmless). A request that is sent again, such as the retry of the Basic authentication after an expired ticket, cannot replay it and
	 * fails with a 401 {@link fr.becpg.api.model.RemoteAPIException}. A failure while reading the content is rethrown as a
	 * {@link org.springframework.web.reactive.function.client.WebClientRequestException} whose cause chain holds the original exception.
	 *
	 * @param upload the document, the file name, the kind of version and its comment
	 * @param content the content of the new version
	 * @return the updated document
	 * @throws fr.becpg.api.model.RemoteAPIException with the status of the webscript on error
	 */
	RemoteUploadResult uploadDocumentVersion(RemoteVersionUpload upload, InputStream content);

}
