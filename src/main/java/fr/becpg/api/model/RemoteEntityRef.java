package fr.becpg.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>RemoteEntityRef class.</p>
 *
 * @author matthieu
 * @version $Id: $Id
 */
public class RemoteEntityRef {

	@JsonProperty(value = "entity")
	private RemoteEntity entity;
	
	/**
	 * <p>Constructor for RemoteEntityRef.</p>
	 */
	public RemoteEntityRef() {
		super();
	}

	/**
	 * <p>Constructor for RemoteEntityRef.</p>
	 *
	 * @param id a {@link java.lang.String} object
	 */
	public RemoteEntityRef(String id) {
		super();
		entity = new RemoteEntity();
		entity.setId(id);
	}
	
	/**
	 * <p>Constructor for RemoteEntityRef.</p>
	 *
	 * @param entity a {@link fr.becpg.api.model.RemoteEntity} object
	 */
	public RemoteEntityRef(RemoteEntity entity) {
		super();
		this.entity = entity;
	}

	/**
	 * <p>Getter for the field <code>entity</code>.</p>
	 *
	 * @return a {@link fr.becpg.api.model.RemoteEntity} object
	 */
	public RemoteEntity getEntity() {
		return entity;
	}

	/**
	 * <p>Setter for the field <code>entity</code>.</p>
	 *
	 * @param entity a {@link fr.becpg.api.model.RemoteEntity} object
	 */
	public void setEntity(RemoteEntity entity) {
		this.entity = entity;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "EntityRef [entity=" + entity + "]";
	}
	
	
}
