package fr.becpg.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class RemoteAPIResponse {

	@JsonProperty("status")
	private String status;

	@JsonProperty("nodeRef")
	private String nodeRef;

	public String getStatus() {
		return status;
	}

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

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNodeRef() {
		return nodeRef;
	}

	public void setNodeRef(String nodeRef) {
		this.nodeRef = nodeRef;
	}

}
