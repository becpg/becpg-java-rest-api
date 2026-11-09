package fr.becpg.api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import tools.jackson.core.StreamReadFeature;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

/**
 * Unit tests for the round trip of a remote API response through {@link RemoteAPIResponse}.
 */
class RemoteAPIResponseTest {

	private static final String RESPONSE = """
			{ "status": "SUCCESS", "nodeRef": "workspace://SpacesStore/0c5f3e2a-7b1d-4c8e-9f6a-2d4b8e1c3a70" }
			""";

	private final JsonMapper mapper = JsonMapper.builder().build();

	// Fails when a key is written twice, which a tree comparison alone would merge
	private final JsonMapper strictMapper = JsonMapper.builder().enable(StreamReadFeature.STRICT_DUPLICATE_DETECTION).build();

	@Test
	void writesTheRemoteResponseBackUnchanged() {
		JsonNode remote = mapper.readTree(RESPONSE);

		RemoteAPIResponse response = mapper.treeToValue(remote, RemoteAPIResponse.class);

		String written = mapper.writeValueAsString(response);

		assertEquals(remote, strictMapper.readTree(written));
	}

	@Test
	void readsTheIdOfTheNode() {
		RemoteAPIResponse response = mapper.readValue(RESPONSE, RemoteAPIResponse.class);

		assertEquals("0c5f3e2a-7b1d-4c8e-9f6a-2d4b8e1c3a70", response.getId());
	}
}
