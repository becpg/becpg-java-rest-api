package fr.becpg.api.model;

/**
 * Result of a copy or a move of one node, as answered by the Share document library webscripts.
 *
 * @param nodeRef the nodeRef of the new node for a copy, of the moved node for a move
 * @param name the name the node has in its destination folder
 */
public record RemoteNodeActionResult(String nodeRef, String name) {
}
