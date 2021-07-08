package fr.becpg.api.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RemoteSiteInfo {

	@JsonProperty("id")
	String id;
	
	@JsonProperty("name")
	String name;

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

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

	@Override
	public String toString() {
		return "RemoteSiteInfo [id=" + id + ", name=" + name + "]";
	}
	
	
}
