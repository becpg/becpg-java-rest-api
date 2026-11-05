package fr.becpg.api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import tools.jackson.databind.json.JsonMapper;

/**
 * Unit tests for the <code>definedBy</code> keyword of {@link RemoteEntitySchema}.
 */
class RemoteEntitySchemaTest {

	private static final String FIELD_SCHEMA = """
			{
			  "type": "array",
			  "title": "Geographic origin",
			  "definedBy": { "kind": "aspect", "name": "bcpg:productGeoOriginAspect" }
			}
			""";

	private final JsonMapper mapper = JsonMapper.builder().build();

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
}
