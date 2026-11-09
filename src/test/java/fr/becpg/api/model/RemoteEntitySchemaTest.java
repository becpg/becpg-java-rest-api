package fr.becpg.api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import tools.jackson.core.StreamReadFeature;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

/**
 * Unit tests for the keywords of {@link RemoteEntitySchema} and its round trip.
 */
class RemoteEntitySchemaTest {

	private static final String FIELD_SCHEMA = """
			{
			  "type": "array",
			  "title": "Geographic origin",
			  "definedBy": { "kind": "aspect", "name": "bcpg:productGeoOriginAspect" }
			}
			""";

	private static final String ENTITY_SCHEMA = """
			{
			  "$schema": "https://json-schema.org/draft/2020-12/schema",
			  "$id": "https://becpg.example:443/becpg/remote/entity/schema",
			  "$locale": "fr",
			  "$supportedLocales": "fr,en",
			  "type": "object",
			  "title": "Entity",
			  "required": [ "cm:name" ],
			  "$comment": "A keyword the client model does not declare",
			  "properties": {
			    "bcpg:productState": {
			      "type": "string",
			      "title": "State",
			      "enum": [ "Simulation", "Valid" ],
			      "default": "Simulation",
			      "definedBy": { "kind": "type", "name": "bcpg:product" },
			      "maxLength": 20,
			      "minLength": 0
			    },
			    "bcpg:productQty": { "type": "number", "title": "Net quantity", "minimum": 0.0, "maximum": 1000.0 },
			    "bcpg:clients": { "type": "array", "title": "Clients", "items": { "type": "object", "title": "Client" } },
			    "content": { "type": "string", "title": "Base64 Content", "contentEncoding": "base64" }
			  }
			}
			""";

	private final JsonMapper mapper = JsonMapper.builder().build();

	// Fails when a key is written twice, which a tree comparison alone would merge
	private final JsonMapper strictMapper = JsonMapper.builder().enable(StreamReadFeature.STRICT_DUPLICATE_DETECTION).build();

	@Test
	void readsTheClassDeclaringAField() {
		RemoteEntitySchema schema = mapper.readValue(FIELD_SCHEMA, RemoteEntitySchema.class);

		assertNotNull(schema.getDefinedBy(), "definedBy not read");
		assertEquals("aspect", schema.getDefinedBy().kind());
		assertEquals("bcpg:productGeoOriginAspect", schema.getDefinedBy().name());
	}

	@Test
	void distinguishesFieldsDeclaredByDifferentClasses() {
		RemoteEntitySchema aspectField = mapper.readValue(FIELD_SCHEMA, RemoteEntitySchema.class);
		RemoteEntitySchema typeField = mapper.readValue(FIELD_SCHEMA, RemoteEntitySchema.class);
		typeField.setDefinedBy(new RemoteEntitySchema.DefinedBy("type", "bcpg:ingList"));

		assertEquals("array", typeField.getType());
		assertNotEquals(aspectField, typeField);
	}

	@Test
	void writesTheRemoteSchemaBackUnchanged() {
		JsonNode remote = mapper.readTree(ENTITY_SCHEMA);

		RemoteEntitySchema schema = mapper.treeToValue(remote, RemoteEntitySchema.class);

		String written = mapper.writeValueAsString(schema);

		assertEquals(remote, strictMapper.readTree(written));
	}

	@Test
	void readsTheAllowedAndDefaultValuesOfAField() {
		RemoteEntitySchema schema = mapper.readValue(ENTITY_SCHEMA, RemoteEntitySchema.class);

		RemoteEntitySchema stateSchema = schema.getAttributeSchema("bcpg:productState");

		assertEquals(List.of("Simulation", "Valid"), stateSchema.getEnumValues());
		assertEquals("Simulation", stateSchema.getDefaultValue());
	}

	@Test
	void readsTheEncodingOfTheContent() {
		RemoteEntitySchema schema = mapper.readValue(ENTITY_SCHEMA, RemoteEntitySchema.class);

		assertEquals("base64", schema.getAttributeSchema("content").getContentEncoding());
	}
}
