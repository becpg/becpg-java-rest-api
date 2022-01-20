package fr.becpg.api.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import fr.becpg.api.helper.DateExtractorHelper;

@JsonInclude(Include.NON_NULL)
public class RemoteNodeInfo {

	@JsonProperty("parent")
	private String parent;

	@JsonProperty("id")
	private String id;

	@JsonProperty("cm:name")
	private String name;

	@JsonProperty("bcpg:code")
	String code;

	@JsonProperty("bcpg:erpCode")
	String erpCode;

	@JsonProperty("site")
	private RemoteSiteInfo site;

	@JsonProperty("path")
	String path;

	@JsonProperty("attributes")
	private Map<String, Object> attributes;

	private Map<String, Object> optionalIdentifiers;

	public RemoteNodeInfo() {

	}

	@SuppressWarnings("unchecked")
	public RemoteNodeInfo(Map<String, Object> fields) {
		this.optionalIdentifiers = fields;
		this.id = (String) fields.get("id");
		this.name = (String) fields.get("cm:name");
		if (this.name == null) {
			this.name = (String) fields.get("bcpg:lkvValue");
		}
		if (this.name == null) {
			this.name = (String) fields.get("bcpg:lvValue");
		}
		if (this.name == null) {
			this.name = (String) fields.get("bcpg:charactName");
		}
		this.code = (String) fields.get("bcpg:code");
		this.erpCode = (String) fields.get("bcpg:erpCode");
		this.parent = (String) fields.get("parent");
		this.path = (String) fields.get("path");
		this.attributes = (Map<String, Object>) fields.get("attributes");
	}

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getErpCode() {
		return erpCode;
	}

	public void setErpCode(String erpCode) {
		this.erpCode = erpCode;
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

	@Nullable
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	@Nullable
	public Boolean getBooleanProp(String propName) {
		return attributes != null ? (Boolean) attributes.get(propName) : null;
	}

	@Nullable
	public String getStringProp(String propName) {
		return attributes != null ? (String) attributes.get(propName) : null;
	}
	
	
	@SuppressWarnings("unchecked")
	@Nullable
	public List<String> getStringArrayProp(String propName) {
		return attributes != null ? (List<String>) attributes.get(propName) : null;
	}

	@Nullable
	public Date getDateProp(String propName) {
		return attributes != null ? DateExtractorHelper.parse((String) attributes.get(propName)) : null;
	}
	
	@Nullable
	public Integer getIntValue(String propName) {
		 return attributes != null ? (Integer) attributes.get(propName) : null;
	}
	
	@Nullable
	public Double getDoubleValue(String propName) {
		if(attributes!=null) {
			if(attributes.get(propName) instanceof Double) {
				return  (Double) attributes.get(propName);
			}
			
			if(attributes.get(propName) instanceof Number) {
				return ((Integer)attributes.get(propName)).doubleValue();
			}
		}
		 return null;
	}
	

	@JsonAnyGetter
	public Map<String, Object> getOptionalIdentifiers() {
		return optionalIdentifiers;
	}

	@JsonAnySetter
	public void setOptionalIdentifiers(Map<String, Object> identifiers) {
		this.optionalIdentifiers = identifiers;
	}

	
	@NonNull
	public List<RemoteNodeInfo> getAssociations(String assocName) {
		List<RemoteNodeInfo> ret = new ArrayList<>();
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> assocs = attributes != null ? (List<Map<String, Object>>) attributes.get(assocName) : null;
		if (assocs != null) {
			for (Map<String, Object> assoc : assocs) {
				ret.add(new RemoteNodeInfo(assoc));
			}

		}
		return ret;
	}

	@Nullable
	public RemoteNodeInfo getAssociation(String assocName) {
		@SuppressWarnings("unchecked")
		Map<String, Object> ret = attributes != null ? (Map<String, Object>) attributes.get(assocName) : null;
		if (ret != null) {
			return new RemoteNodeInfo(ret);
		}
		return null;
	}

	
	@NonNull
	public List<RemoteNodeInfo> getDocuments() {
		List<RemoteNodeInfo> ret = new ArrayList<>();
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> assocs = attributes != null ? (List<Map<String, Object>>) attributes.get("cm:contains") : null;
		if (assocs != null) {
			for (Map<String, Object> assoc : assocs) {
				ret.add(new RemoteNodeInfo(assoc));
			}

		}
		return ret;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(attributes, code, erpCode, id, name, optionalIdentifiers, parent, path, site);
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
		return Objects.equals(attributes, other.attributes) && Objects.equals(code, other.code) && Objects.equals(erpCode, other.erpCode)
				&& Objects.equals(id, other.id) && Objects.equals(name, other.name) && Objects.equals(optionalIdentifiers, other.optionalIdentifiers)
				&& Objects.equals(parent, other.parent) && Objects.equals(path, other.path) && Objects.equals(site, other.site);
	}

	@Override
	public String toString() {
		return "RemoteNodeInfo [parent=" + parent + ", id=" + id + ", name=" + name + ", code=" + code + ", erpCode=" + erpCode + ", site=" + site
				+ ", path=" + path + ", attributes=" + attributes + ", optionalIdentifiers=" + optionalIdentifiers + "]";
	}


}
