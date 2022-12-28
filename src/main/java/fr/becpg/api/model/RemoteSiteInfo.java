package fr.becpg.api.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>RemoteSiteInfo class.</p>
 *
 * @author matthieu
 * @version $Id: $Id
 */
public class RemoteSiteInfo {

	@JsonProperty("id")
	String id;
	
	@JsonProperty("name")
	String name;

	/**
	 * <p>Getter for the field <code>id</code>.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getId() {
		return id;
	}

	/**
	 * <p>Getter for the field <code>name</code>.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getName() {
		return name;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RemoteSiteInfo other = (RemoteSiteInfo) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "RemoteSiteInfo [id=" + id + ", name=" + name + "]";
	}
	
	
}
