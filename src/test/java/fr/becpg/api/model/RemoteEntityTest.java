package fr.becpg.api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.Test;

import tools.jackson.core.StreamReadFeature;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

/**
 * Unit tests for the round trip of a remote entity through {@link RemoteEntity} and {@link RemoteNodeInfo}.
 */
class RemoteEntityTest {

	private static final String REMOTE_ENTITY = """
			{
			  "entity": {
			    "id": "0c5f3e2a-7b1d-4c8e-9f6a-2d4b8e1c3a70",
			    "type": "bcpg:finishedProduct",
			    "cm:name": "Pizza",
			    "bcpg:code": "PF12",
			    "site": { "id": "simulation", "name": "Simulation" },
			    "version": "1.0",
			    "metadata": { "cm:created": "2026-11-02T10:00:00.000+01:00" },
			    "attributes": {
			      "bcpg:legalName": null,
			      "bcpg:clients": [ { "bcpg:code": "C1" } ]
			    },
			    "datalists": {
			      "bcpg:compoList": [
			        {
			          "id": "2c3d4e5f-6a7b-4c8d-9e0f-1a2b3c4d5e6f",
			          "type": "bcpg:compoList",
			          "entityId": "0c5f3e2a-7b1d-4c8e-9f6a-2d4b8e1c3a70",
			          "bcpg:compoListProduct": { "bcpg:code": "MP12" },
			          "attributes": { "bcpg:sort": 3, "bcpg:compoListQty": 1.5 }
			        }
			      ]
			    },
			    "params": { "dataListsToReplace": "bcpg:compoList" }
			  }
			}
			""";

	private static final String VERSION = "version";
	private static final String METADATA = "metadata";

	private final JsonMapper mapper = JsonMapper.builder().build();

	// Fails when a key is written twice, which a tree comparison alone would merge
	private final JsonMapper strictMapper = JsonMapper.builder().enable(StreamReadFeature.STRICT_DUPLICATE_DETECTION).build();

	@Test
	void writesTheRemoteEntityBackUnchanged() {
		JsonNode remote = mapper.readTree(REMOTE_ENTITY);

		RemoteEntityRef entityRef = mapper.treeToValue(remote, RemoteEntityRef.class);

		String written = mapper.writeValueAsString(entityRef);

		assertEquals(remote, strictMapper.readTree(written));
	}

	@Test
	void exposesTheKeysOutsideTheModelAsOptionalIdentifiers() {
		RemoteEntity entity = mapper.readValue(REMOTE_ENTITY, RemoteEntityRef.class).getEntity();

		Map<String, Object> optionalIdentifiers = entity.getOptionalIdentifiers();

		assertEquals(Map.of(VERSION, "1.0", METADATA, Map.of("cm:created", "2026-11-02T10:00:00.000+01:00")), optionalIdentifiers);
	}

	@Test
	void writesTheIdentifiersSetByTheCaller() {
		RemoteEntity entity = new RemoteEntity();
		entity.setOptionalIdentifiers(Map.of("bcpg:channelId", "web"));

		JsonNode written = mapper.valueToTree(entity);

		assertEquals(mapper.readTree("{\"bcpg:channelId\": \"web\"}"), written);
	}
}
