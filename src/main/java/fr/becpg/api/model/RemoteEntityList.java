package fr.becpg.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RemoteEntityList {

	@JsonProperty("entities")
	List<RemoteEntityRef> entities;

	public List<RemoteEntityRef> getEntities() {
		return entities;
	}

	public void setEntities(List<RemoteEntityRef> entities) {
		this.entities = entities;
	}

}
