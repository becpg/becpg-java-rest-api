package fr.becpg.api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import tools.jackson.core.StreamReadFeature;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

/**
 * Unit tests for the round trip of a remote entity page through {@link RemoteEntityList} and {@link Pagination}.
 */
class RemoteEntityListTest {

	private static final String PAGE_WITH_TOTAL = """
			{
			  "entities": [ { "entity": { "id": "3d4e5f6a-7b8c-4d9e-8f0a-1b2c3d4e5f6a", "cm:name": "Flour", "version": "2.0" } } ],
			  "pagination": { "hasMoreItems": true, "count": 1, "totalItems": 42 }
			}
			""";

	private static final String PAGE_WITHOUT_TOTAL = """
			{
			  "entities": [ { "entity": { "id": "3d4e5f6a-7b8c-4d9e-8f0a-1b2c3d4e5f6a" } } ],
			  "pagination": { "hasMoreItems": false, "count": 1 }
			}
			""";

	private final JsonMapper mapper = JsonMapper.builder().build();

	// Fails when a key is written twice, which a tree comparison alone would merge
	private final JsonMapper strictMapper = JsonMapper.builder().enable(StreamReadFeature.STRICT_DUPLICATE_DETECTION).build();

	@ParameterizedTest
	@ValueSource(strings = { PAGE_WITH_TOTAL, PAGE_WITHOUT_TOTAL })
	void writesTheRemotePageBackUnchanged(String page) {
		JsonNode remote = mapper.readTree(page);

		RemoteEntityList entityList = mapper.treeToValue(remote, RemoteEntityList.class);

		String written = mapper.writeValueAsString(entityList);

		assertEquals(remote, strictMapper.readTree(written));
	}

	@Test
	void readsTheTotalOfMatchingEntities() {
		RemoteEntityList entityList = mapper.readValue(PAGE_WITH_TOTAL, RemoteEntityList.class);

		assertEquals(42, entityList.getPagination().getTotalItems());
	}

	@Test
	void keepsThePageSizeAsTotalItemsForExistingCallers() {
		RemoteEntityList entityList = mapper.readValue(PAGE_WITH_TOTAL, RemoteEntityList.class);

		assertEquals(1, entityList.getTotalItems());
	}
}
