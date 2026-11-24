package fr.becpg.api.model;

/**
 * Parameters of the upload of a new document into a folder.
 *
 * @param folderId the destination folder, a uuid or a nodeRef
 * @param fileName the name of the document in the folder
 * @param nodeType an optional Alfresco type of the document, null means the default one
 */
public record RemoteDocumentUpload(String folderId, String fileName, String nodeType) {
}
