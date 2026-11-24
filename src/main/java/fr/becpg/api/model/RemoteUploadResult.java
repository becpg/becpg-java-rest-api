package fr.becpg.api.model;

/**
 * Answer of the Share upload webscript.
 *
 * @param nodeRef the nodeRef of the created or updated document
 * @param fileName the name of the document, which may differ from the requested one when the folder already holds a file of that name
 */
public record RemoteUploadResult(String nodeRef, String fileName) {
}
