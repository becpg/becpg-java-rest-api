package fr.becpg.api.model;

import java.util.List;

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
	
	public Pagination getPagination() {
		return pagination;
	}
	
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
	
	public boolean hasMoreItems() {
		return pagination != null && pagination.isHasMoreItems();
	}
	
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
