package fr.becpg.api.model;

/**
 * Parameters of the upload of a new version of an existing document.
 *
 * @param documentId the document to version, a uuid or a nodeRef
 * @param fileName the file name sent with the content
 * @param majorVersion true for a major version, false for a minor one
 * @param comment the version comment, may be null
 */
public record RemoteVersionUpload(String documentId, String fileName, boolean majorVersion, String comment) {
}
