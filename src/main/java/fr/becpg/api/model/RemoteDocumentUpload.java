package fr.becpg.api.model;

/**
 * Parameters of the upload of a new document into a folder.
 *
 * @param folderId the destination folder, a uuid or a nodeRef
 * @param fileName the name of the document in the folder
 */
public record RemoteDocumentUpload(String folderId, String fileName) {
}
