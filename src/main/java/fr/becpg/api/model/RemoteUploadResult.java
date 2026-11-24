package fr.becpg.api.model;

/**
 * Answer of the Share upload webscript.
 *
 * @param nodeRef the nodeRef of the created or updated document
 * @param fileName the name of the document
 * @param statusCode the status code reported by the webscript
 */
public record RemoteUploadResult(String nodeRef, String fileName, int statusCode) {
}
