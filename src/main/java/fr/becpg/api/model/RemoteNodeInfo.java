package fr.becpg.api.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import fr.becpg.api.helper.DateExtractorHelper;

/**
 * <p>RemoteNodeInfo class.</p>
 *
 * @author matthieu
 * @version $Id: $Id
 */
@JsonInclude(Include.NON_NULL)
public class RemoteNodeInfo {

	@JsonProperty("parent")
	private String parent;

	@JsonProperty("id")
	private String id;

	@JsonProperty("cm:name")
	private String name;

	@JsonProperty("bcpg:code")
	private String code;

	@JsonProperty("bcpg:erpCode")
	private String erpCode;

	@JsonProperty("site")
	private RemoteSiteInfo site;

	@JsonProperty("path")
	private String path;

	@JsonProperty("type")
	private String type;
	
	@JsonProperty("content")
	private String content;

	@JsonProperty("attributes")
	private Map<String, Object> attributes;

	private Map<String, Object> optionalIdentifiers;

	/**
	 * <p>Constructor for RemoteNodeInfo.</p>
	 */
	public RemoteNodeInfo() {

	}

	@SuppressWarnings("unchecked")
	/**
	 * <p>Constructor for RemoteNodeInfo.</p>
	 *
	 * @param fields a {@link java.util.Map} object
	 */
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
		if (this.name == null) {
			this.name = (String) fields.get("cm:userName");
		}
		if (this.name == null) {
			this.name = (String) fields.get("cm:authorityName");
		}
		this.code = (String) fields.get("bcpg:code");
		this.type = (String) fields.get("type");
		this.erpCode = (String) fields.get("bcpg:erpCode");
		this.parent = (String) fields.get("parent");
		this.path = (String) fields.get("path");
		this.attributes = (Map<String, Object>) fields.get("attributes");
	}

	/**
	 * <p>Getter for the field <code>parent</code>.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getParent() {
		return parent;
	}

	/**
	 * <p>Setter for the field <code>parent</code>.</p>
	 *
	 * @param parent a {@link java.lang.String} object
	 */
	public void setParent(String parent) {
		this.parent = parent;
	}

	/**
	 * <p>Getter for the field <code>id</code>.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getId() {
		return id;
	}

	/**
	 * <p>Setter for the field <code>id</code>.</p>
	 *
	 * @param id a {@link java.lang.String} object
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * <p>Getter for the field <code>name</code>.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getName() {
		return name;
	}

	/**
	 * <p>Setter for the field <code>name</code>.</p>
	 *
	 * @param name a {@link java.lang.String} object
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * <p>Getter for the field <code>code</code>.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getCode() {
		return code;
	}

	/**
	 * <p>Setter for the field <code>code</code>.</p>
	 *
	 * @param code a {@link java.lang.String} object
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * <p>Getter for the field <code>erpCode</code>.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getErpCode() {
		return erpCode;
	}

	/**
	 * <p>Setter for the field <code>erpCode</code>.</p>
	 *
	 * @param erpCode a {@link java.lang.String} object
	 */
	public void setErpCode(String erpCode) {
		this.erpCode = erpCode;
	}

	/**
	 * <p>Getter for the field <code>site</code>.</p>
	 *
	 * @return a {@link fr.becpg.api.model.RemoteSiteInfo} object
	 */
	public RemoteSiteInfo getSite() {
		return site;
	}

	/**
	 * <p>Setter for the field <code>site</code>.</p>
	 *
	 * @param site a {@link fr.becpg.api.model.RemoteSiteInfo} object
	 */
	public void setSite(RemoteSiteInfo site) {
		this.site = site;
	}

	/**
	 * <p>Getter for the field <code>path</code>.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getPath() {
		return path;
	}

	/**
	 * <p>Setter for the field <code>path</code>.</p>
	 *
	 * @param path a {@link java.lang.String} object
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * <p>Getter for the field <code>type</code>.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getType() {
		return type;
	}

	/**
	 * <p>Setter for the field <code>type</code>.</p>
	 *
	 * @param type a {@link java.lang.String} object
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * <p>Getter for the field <code>attributes</code>.</p>
	 *
	 * @return a {@link java.util.Map} object
	 */
	@Nullable
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	/**
	 * <p>Setter for the field <code>attributes</code>.</p>
	 *
	 * @param attributes a {@link java.util.Map} object
	 */
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	
	
	/**
	 * <p>Getter for the field <code>content</code>.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	@Nullable
	public String getContent() {
		return content;
	}

	/**
	 * <p>Setter for the field <code>content</code>.</p>
	 *
	 * @param attributes a {@link java.lang.String} object
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * <p>getBooleanProp.</p>
	 *
	 * @param propName a {@link java.lang.String} object
	 * @return a {@link java.lang.Boolean} object
	 */
	@Nullable
	@JsonIgnore
	public Boolean getBooleanProp(String propName) {
		return attributes != null ? (Boolean) attributes.get(propName) : null;
	}

	/**
	 * <p>getStringProp.</p>
	 *
	 * @param propName a {@link java.lang.String} object
	 * @return a {@link java.lang.String} object
	 */
	@Nullable
	@JsonIgnore
	public String getStringProp(String propName) {
		return attributes != null ? (String) attributes.get(propName) : null;
	}

	/**
	 * <p>getStringArrayProp.</p>
	 *
	 * @param propName a {@link java.lang.String} object
	 * @return a {@link java.util.List} object
	 */
	@SuppressWarnings("unchecked")
	@Nullable
	@JsonIgnore
	public List<String> getStringArrayProp(String propName) {
		return attributes != null ? (List<String>) attributes.get(propName) : null;
	}

	/**
	 * <p>getDateProp.</p>
	 *
	 * @param propName a {@link java.lang.String} object
	 * @return a {@link java.util.Date} object
	 */
	@Nullable
	@JsonIgnore
	public Date getDateProp(String propName) {
		return attributes != null && attributes.get(propName)!=null ? DateExtractorHelper.parse((String) attributes.get(propName)) : null;
	}

	/**
	 * <p>getProp.</p>
	 *
	 * @param propName a {@link java.lang.String} object
	 * @return a {@link java.lang.Object} object
	 */
	@Nullable
	@JsonIgnore
	public Object getProp(String propName) {
		return attributes != null ? attributes.get(propName) : null;
	}

	/**
	 * <p>getIntValue.</p>
	 *
	 * @param propName a {@link java.lang.String} object
	 * @return a {@link java.lang.Integer} object
	 */
	@Nullable
	@JsonIgnore
	public Integer getIntValue(String propName) {
		return attributes != null ? (Integer) attributes.get(propName) : null;
	}

	/**
	 * <p>getDoubleValue.</p>
	 *
	 * @param propName a {@link java.lang.String} object
	 * @return a {@link java.lang.Double} object
	 */
	@Nullable
	@JsonIgnore
	public Double getDoubleValue(String propName) {
		if (attributes != null) {
			if (attributes.get(propName) instanceof Double) {
				return (Double) attributes.get(propName);
			}

			if (attributes.get(propName) instanceof Number) {
				return ((Integer) attributes.get(propName)).doubleValue();
			}
		}
		return null;
	}

	/**
	 * <p>getSort.</p>
	 *
	 * @return a {@link java.lang.Integer} object
	 */
	@Nullable
	public Integer getSort() {
		return getIntValue("bcpg:sort");
	}

	/**
	 * <p>Getter for the field <code>optionalIdentifiers</code>.</p>
	 *
	 * @return a {@link java.util.Map} object
	 */
	@JsonAnyGetter
	public Map<String, Object> getOptionalIdentifiers() {
		return optionalIdentifiers;
	}

	/**
	 * <p>Setter for the field <code>optionalIdentifiers</code>.</p>
	 *
	 * @param identifiers a {@link java.util.Map} object
	 */
	@JsonAnySetter
	public void setOptionalIdentifiers(Map<String, Object> identifiers) {
		this.optionalIdentifiers = identifiers;
	}

	/**
	 * <p>getAssociations.</p>
	 *
	 * @param assocName a {@link java.lang.String} object
	 * @return a {@link java.util.List} object
	 */
	@NonNull
	@JsonIgnore
	public List<RemoteNodeInfo> getAssociations(String assocName) {
		List<RemoteNodeInfo> ret = new ArrayList<>();
		
		if (attributes != null && attributes.get(assocName) instanceof List) {
			
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> assocs = (List<Map<String, Object>>) attributes.get(assocName);
			if (assocs != null) {
				for (Map<String, Object> assoc : assocs) {
					ret.add(new RemoteNodeInfo(assoc));
				}
			}
			
		} else {
			
			@SuppressWarnings("unchecked")
			Map<String, Object> assoc = attributes != null ? (Map<String, Object>) attributes.get(assocName) : null;
			if (assoc != null) {
				ret.add(new RemoteNodeInfo(assoc));
			}
			
		}
		
		return ret;
	}

	/**
	 * <p>getAssociation.</p>
	 *
	 * @param assocName a {@link java.lang.String} object
	 * @return a {@link fr.becpg.api.model.RemoteNodeInfo} object
	 */
	@Nullable
	@JsonIgnore
	public RemoteNodeInfo getAssociation(String assocName) {
		if (attributes != null && attributes.get(assocName) instanceof List) {
			List<RemoteNodeInfo> ret = getAssociations(assocName);
			if (!ret.isEmpty()) {
				return ret.get(0);
			}
		} else {

			@SuppressWarnings("unchecked")
			Map<String, Object> ret = attributes != null ? (Map<String, Object>) attributes.get(assocName) : null;
			if (ret != null) {
				return new RemoteNodeInfo(ret);
			}
		}
		return null;
	}

	/**
	 * <p>getDocuments.</p>
	 *
	 * @return a {@link java.util.List} object
	 */
	@NonNull
	@JsonIgnore
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
	
	/**
	 * Allow to merge two entities
	 * @param entity
	 */
	public void merge(RemoteEntity entity) {
		if(entity.getAttributes()!=null) {
			if(attributes == null) {
				attributes = new HashMap<>();
			}
			attributes.putAll(entity.getAttributes());
		}
		
		if(entity.getOptionalIdentifiers()!=null) {
			if(optionalIdentifiers == null) {
				optionalIdentifiers = new HashMap<>();
			}
			optionalIdentifiers.putAll(entity.getOptionalIdentifiers());
		}
	}


	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return Objects.hash(attributes, code, erpCode, id, name, optionalIdentifiers, parent, path, site, type);
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
		RemoteNodeInfo other = (RemoteNodeInfo) obj;
		return Objects.equals(attributes, other.attributes) && Objects.equals(code, other.code) && Objects.equals(erpCode, other.erpCode)
				&& Objects.equals(id, other.id) && Objects.equals(name, other.name) && Objects.equals(optionalIdentifiers, other.optionalIdentifiers)
				&& Objects.equals(parent, other.parent) && Objects.equals(path, other.path) && Objects.equals(site, other.site)
				&& Objects.equals(type, other.type);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "RemoteNodeInfo [parent=" + parent + ", id=" + id + ", name=" + name + ", code=" + code + ", erpCode=" + erpCode + ", site=" + site
				+ ", path=" + path + ", type=" + type + ", attributes=" + attributes + ", optionalIdentifiers=" + optionalIdentifiers + "]";
	}

	
}
