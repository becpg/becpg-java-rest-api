package fr.becpg.api.model;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>RemoteEntitySchema class.</p>
 *
 * @author matthieu
 * @version $Id: $Id
 */
@JsonInclude(Include.NON_NULL)
public class RemoteEntitySchema {
	
	@JsonProperty("$schema")
	private String schemaVersion;
	
	@JsonProperty("$id")
	private String schemaId;

	@JsonProperty("title")
	private String title;

	@JsonProperty("description")
	private String description;

	@JsonProperty("type")
	private String type;

	@JsonProperty("properties")
	private Map<String, RemoteEntitySchema> properties;
	
	@JsonProperty("required")
	private List<String> required;

	@JsonProperty("items")
	private RemoteEntitySchema items;

	/**
	 * <p>getAttributeSchema.</p>
	 *
	 * @param attributeName a {@link java.lang.String} object
	 * @return a {@link fr.becpg.api.model.RemoteEntitySchema} object
	 */
	@JsonIgnore
	public RemoteEntitySchema getAttributeSchema(String attributeName) {

		if ((properties != null) && properties.containsKey(attributeName)) {
			return properties.get(attributeName);
		}
		return null;
	}
	
	/**
	 * <p>Getter for the field <code>schemaVersion</code>.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getSchemaVersion() {
		return schemaVersion;
	}

	/**
	 * <p>Setter for the field <code>schemaVersion</code>.</p>
	 *
	 * @param schemaVersion a {@link java.lang.String} object
	 */
	public void setSchemaVersion(String schemaVersion) {
		this.schemaVersion = schemaVersion;
	}

	/**
	 * <p>Getter for the field <code>schemaId</code>.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getSchemaId() {
		return schemaId;
	}

	/**
	 * <p>Setter for the field <code>schemaId</code>.</p>
	 *
	 * @param schemaId a {@link java.lang.String} object
	 */
	public void setSchemaId(String schemaId) {
		this.schemaId = schemaId;
	}

	/**
	 * <p>Getter for the field <code>required</code>.</p>
	 *
	 * @return a {@link java.util.List} object
	 */
	public List<String> getRequired() {
		return required;
	}

	/**
	 * <p>Setter for the field <code>required</code>.</p>
	 *
	 * @param required a {@link java.util.List} object
	 */
	public void setRequired(List<String> required) {
		this.required = required;
	}

	/**
	 * <p>Getter for the field <code>title</code>.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * <p>Setter for the field <code>title</code>.</p>
	 *
	 * @param title a {@link java.lang.String} object
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * <p>Getter for the field <code>description</code>.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * <p>Setter for the field <code>description</code>.</p>
	 *
	 * @param description a {@link java.lang.String} object
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * <p>Getter for the field <code>properties</code>.</p>
	 *
	 * @return a {@link java.util.Map} object
	 */
	public Map<String, RemoteEntitySchema> getProperties() {
		return properties;
	}

	/**
	 * <p>Setter for the field <code>properties</code>.</p>
	 *
	 * @param properties a {@link java.util.Map} object
	 */
	public void setProperties(Map<String, RemoteEntitySchema> properties) {
		this.properties = properties;
	}

	/**
	 * <p>Getter for the field <code>items</code>.</p>
	 *
	 * @return a {@link fr.becpg.api.model.RemoteEntitySchema} object
	 */
	public RemoteEntitySchema getItems() {
		return items;
	}

	/**
	 * <p>Setter for the field <code>items</code>.</p>
	 *
	 * @param items a {@link fr.becpg.api.model.RemoteEntitySchema} object
	 */
	public void setItems(RemoteEntitySchema items) {
		this.items = items;
	}


	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return Objects.hash(description, items, properties, required, schemaId, schemaVersion, title, type);
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
		RemoteEntitySchema other = (RemoteEntitySchema) obj;
		return Objects.equals(description, other.description) && Objects.equals(items, other.items) && Objects.equals(properties, other.properties)
				&& Objects.equals(required, other.required) && Objects.equals(schemaId, other.schemaId)
				&& Objects.equals(schemaVersion, other.schemaVersion) && Objects.equals(title, other.title) && Objects.equals(type, other.type);
	}

	
	
}
