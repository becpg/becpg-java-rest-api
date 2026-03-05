package fr.becpg.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>RemoteAPIResponse class.</p>
 *
 * @author matthieu
 */
@JsonInclude(Include.NON_NULL)
public class RemoteAPIResponse {

	@JsonProperty("status")
	private String status;

	@JsonProperty("nodeRef")
	private String nodeRef;

	/**
	 * <p>Getter for the field <code>status</code>.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * <p>getId.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getId() {
		if (nodeRef != null) {
			int lastForwardSlash = nodeRef.lastIndexOf('/');
			if (lastForwardSlash == -1) {
				throw new IllegalStateException("Invalid node ref - does not contain forward slash: " + nodeRef);
			}
			return nodeRef.substring(lastForwardSlash + 1);

		}

		return null;

	}

	/**
	 * <p>Setter for the field <code>status</code>.</p>
	 *
	 * @param status a {@link java.lang.String} object
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * <p>Getter for the field <code>nodeRef</code>.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getNodeRef() {
		return nodeRef;
	}

	/**
	 * <p>Setter for the field <code>nodeRef</code>.</p>
	 *
	 * @param nodeRef a {@link java.lang.String} object
	 */
	public void setNodeRef(String nodeRef) {
		this.nodeRef = nodeRef;
	}

}
