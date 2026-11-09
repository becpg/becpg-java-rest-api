package fr.becpg.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>RemoteEntityList class.</p>
 *
 * @author matthieu
 * @version $Id: $Id
 */
public class RemoteEntityList {

	@JsonProperty("entities")
	private List<RemoteEntityRef> entities;

	@JsonProperty("pagination")
	private Pagination pagination;
	
	/**
	 * <p>Getter for the field <code>pagination</code>.</p>
	 *
	 * @return a {@link fr.becpg.api.model.Pagination} object
	 */
	public Pagination getPagination() {
		return pagination;
	}
	
	/**
	 * <p>Setter for the field <code>pagination</code>.</p>
	 *
	 * @param pagination a {@link fr.becpg.api.model.Pagination} object
	 */
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
	
	/**
	 * <p>hasMoreItems.</p>
	 *
	 * @return a boolean
	 */
	public boolean hasMoreItems() {
		return pagination != null && pagination.isHasMoreItems();
	}
	
	/**
	 * <p>getTotalItems.</p>
	 *
	 * @return the number of entities of this page (<code>pagination.count</code>), never serialized; the total of
	 *         matching entities is {@link Pagination#getTotalItems()}
	 */
	@JsonIgnore
	public int getTotalItems() {
		return pagination == null ? 0 : pagination.getCount();
	}
	
	/**
	 * <p>Getter for the field <code>entities</code>.</p>
	 *
	 * @return a {@link java.util.List} object
	 */
	public List<RemoteEntityRef> getEntities() {
		return entities;
	}

	/**
	 * <p>Setter for the field <code>entities</code>.</p>
	 *
	 * @param entities a {@link java.util.List} object
	 */
	public void setEntities(List<RemoteEntityRef> entities) {
		this.entities = entities;
	}

}
