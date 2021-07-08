package fr.becpg.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RemoteEntityRef {

	@JsonProperty(value = "entity")
	private RemoteEntity entity;

	public RemoteEntity getEntity() {
		return entity;
	}

	public void setEntity(RemoteEntity entity) {
		this.entity = entity;
	}

	@Override
	public String toString() {
		return "EntityRef [entity=" + entity + "]";
	}
	
	
}
