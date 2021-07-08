package fr.becpg.api.model;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RemoteNodeInfo {
	
	@JsonProperty("parent")
	private String parent;

	@JsonProperty("id")
	private String id;
	
	@JsonProperty("cm:name")
	private String name;

	@JsonProperty("site")
	private RemoteSiteInfo site;
	
	@JsonProperty("path")
	String path;
	
	@JsonProperty("attributes")
	private Map<String,Object> attributes;
	
	
	private Map<String, Object> optionalIdentifiers;

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RemoteSiteInfo getSite() {
		return site;
	}

	public void setSite(RemoteSiteInfo site) {
		this.site = site;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	
	@JsonAnyGetter
	public Map<String, Object> getOptionalIdentifiers() {
		return optionalIdentifiers;
	}

	@JsonAnySetter
	public void setOptionalIdentifiers(Map<String, Object> identifiers) {
		this.optionalIdentifiers = identifiers;
	}
	
	 public List<Object> getAssociations(String assocName) {
		return (List<Object>) attributes.get(assocName);
	}
	 
	 
	 public Object getAssociation(String assocName) {
		return attributes.get(assocName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(attributes, id, optionalIdentifiers, name, parent, path, site);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RemoteNodeInfo other = (RemoteNodeInfo) obj;
		return Objects.equals(attributes, other.attributes) && Objects.equals(id, other.id) && Objects.equals(optionalIdentifiers, other.optionalIdentifiers)
				&& Objects.equals(name, other.name) && Objects.equals(parent, other.parent) && Objects.equals(path, other.path)
				&& Objects.equals(site, other.site);
	}

	@Override
	public String toString() {
		return "RemoteNodeInfo [parent=" + parent + ", id=" + id + ", name=" + name + ", site=" + site + ", path=" + path + ", attributes="
				+ attributes + ", identifiers=" + optionalIdentifiers + "]";
	}

	
	
}
