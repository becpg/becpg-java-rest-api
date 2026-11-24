package fr.becpg.api.model;

/**
 * Parameters of the copy or the move of one node into a folder.
 *
 * @param id the id or nodeRef of the node
 * @param destFolderId the id or nodeRef of the destination folder
 * @param parentId the id or nodeRef of the current parent of the node, required for a move, optional (may be <code>null</code>) for a copy
 */
public record RemoteNodeRelocation(String id, String destFolderId, String parentId) {
}
