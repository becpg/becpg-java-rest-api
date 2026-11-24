package fr.becpg.api.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import fr.becpg.api.model.RemoteAPIException;
import fr.becpg.api.model.RemoteAuditPage;
import fr.becpg.api.model.RemoteAuditQuery;
import reactor.core.publisher.Mono;

/**
 * Reading of one page of the audit through the {@code /becpg/stats/{type}} webscript, against a stubbed exchange: the
 * parameters are read back as a servlet container decodes them, so a value that is not strictly encoded comes back changed.
 */
class AuditAPIClientTest {

	private static final String BASE_URL = "http://repository/alfresco/service/becpg/remote";

	private static final String FORMULATION = "formulation";

	private static final String ENTITY_FILTER = "entityNodeRef=workspace://SpacesStore/c8e292db-3287-4b2b-b5d2-829aae716ed9";

	private static final String CHAIN_FILTER = "chainId=c1";

	private static final String FROM_DATE = "2026-09-01T02:00:00.000+02:00";

	private static final String TO_DATE = "2026-09-30";

	private static final String DB_ASC = "dbAsc";

	private static final String EMPTY_PAGE = "{\"statistics\":[],\"scanInterrupted\":false}";

	private static final String MIDDLE_PAGE = "{\"statistics\":[{\"id\":40,\"startedAt\":\"2026-09-02T10:00:00.000+02:00\"},{\"id\":41,\"startedAt\":\"2026-09-02T11:00:00.000+02:00\"}],"
			+ "\"nextStartAfterId\":41,\"scanInterrupted\":true}";

	private static final String ALFRESCO_FORBIDDEN = "{\"status\":{\"code\":403,\"name\":\"Forbidden\",\"description\":\"Access denied\"},"
			+ "\"message\":\"Access denied\",\"exception\":\"\",\"callstack\":[]}";

	private static final String BAD_REQUEST = "{\"message\":\"Invalid date\"}";

	private final List<ClientRequest> requests = new ArrayList<>();

	@Test
	void shouldSendEveryParameterAsTheWebscriptReadsIt() {
		AuditAPIClient client = clientAnswering(HttpStatus.OK, EMPTY_PAGE);
		RemoteAuditQuery query = new RemoteAuditQuery(FORMULATION, List.of(ENTITY_FILTER, CHAIN_FILTER), FROM_DATE, TO_DATE, 256, 1L, true);

		client.listEntries(query);

		ClientRequest request = soleRequest();
		assertEquals("/alfresco/service/becpg/stats/formulation", request.url().getPath());
		assertEquals(Map.of("filter", List.of(ENTITY_FILTER, CHAIN_FILTER), "fromDate", List.of(FROM_DATE), "toDate", List.of(TO_DATE), "maxResults",
				List.of("256"), "startAfterId", List.of("1"), DB_ASC, List.of("false")), readDecodedParameters(request));
	}

	@Test
	void shouldSendNothingTheCallerLeftOut() {
		AuditAPIClient client = clientAnswering(HttpStatus.OK, EMPTY_PAGE);

		client.listEntries(new RemoteAuditQuery(FORMULATION, null, null, null, null, null, false));

		assertEquals(Map.of(DB_ASC, List.of("true")), readDecodedParameters(soleRequest()));
	}

	@ParameterizedTest(name = "{0}")
	@ValueSource(strings = { "a/b", ".." })
	void shouldRefuseATypeThatIsNotAPlainNameBeforeAnyCall(String type) {
		AuditAPIClient client = clientAnswering(HttpStatus.OK, EMPTY_PAGE);
		RemoteAuditQuery query = new RemoteAuditQuery(type, null, null, null, null, null, false);

		assertThrows(IllegalArgumentException.class, () -> client.listEntries(query));

		assertTrue(requests.isEmpty());
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("createPageRows")
	void shouldReadThePage(String row, String body, List<Integer> expectedIds, Long expectedCursor, Boolean expectedScanInterrupted) {
		AuditAPIClient client = clientAnswering(HttpStatus.OK, body);

		RemoteAuditPage page = client.listEntries(new RemoteAuditQuery(FORMULATION, null, null, null, null, null, true));

		assertEquals(expectedIds, page.entries().stream().map(entry -> entry.get("id")).toList());
		assertEquals(expectedCursor, page.nextStartAfterId());
		assertEquals(expectedScanInterrupted, page.scanInterrupted());
	}

	static Stream<Arguments> createPageRows() {
		return Stream.of(Arguments.of("middle page", MIDDLE_PAGE, List.of(40, 41), 41L, true),
				Arguments.of("last page", EMPTY_PAGE, List.of(), null, false));
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("createRefusalRows")
	void shouldReportARefusalWithItsStatus(String row, HttpStatus status, String body, String expectedCode) {
		AuditAPIClient client = clientAnswering(status, body);
		RemoteAuditQuery query = new RemoteAuditQuery(FORMULATION, null, null, null, null, null, true);

		RemoteAPIException refusal = assertThrows(RemoteAPIException.class, () -> client.listEntries(query));

		assertEquals(expectedCode, refusal.getError().getStatus().getCode());
	}

	static Stream<Arguments> createRefusalRows() {
		return Stream.of(Arguments.of("forbidden with an Alfresco error body", HttpStatus.FORBIDDEN, ALFRESCO_FORBIDDEN, "403"),
				Arguments.of("bad request", HttpStatus.BAD_REQUEST, BAD_REQUEST, "400"));
	}

	@Test
	void shouldReportAnEmptyAnswerAsARemoteError() {
		AuditAPIClient client = clientAnswering(HttpStatus.OK, null);
		RemoteAuditQuery query = new RemoteAuditQuery(FORMULATION, null, null, null, null, null, true);

		RemoteAPIException failure = assertThrows(RemoteAPIException.class, () -> client.listEntries(query));

		assertEquals("502", failure.getError().getStatus().getCode());
	}

	private AuditAPIClient clientAnswering(HttpStatus status, String body) {
		AuditAPIClient client = new AuditAPIClient();
		client.webClient = WebClient.builder().baseUrl(BASE_URL).exchangeFunction(request -> {
			requests.add(request);
			ClientResponse.Builder response = ClientResponse.create(status).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
			return Mono.just(body == null ? response.build() : response.body(body).build());
		}).build();
		return client;
	}

	private ClientRequest soleRequest() {
		assertEquals(1, requests.size());
		assertTrue(requests.get(0).method().matches("GET"));
		return requests.get(0);
	}

	private static Map<String, List<String>> readDecodedParameters(ClientRequest request) {
		Map<String, List<String>> parameters = new LinkedHashMap<>();
		String rawQuery = request.url().getRawQuery();
		if (rawQuery == null) {
			return parameters;
		}
		for (String pair : rawQuery.split("&")) {
			int separator = pair.indexOf('=');
			String name = URLDecoder.decode(pair.substring(0, separator), StandardCharsets.UTF_8);
			String value = URLDecoder.decode(pair.substring(separator + 1), StandardCharsets.UTF_8);
			parameters.computeIfAbsent(name, key -> new ArrayList<>()).add(value);
		}
		return parameters;
	}

}
