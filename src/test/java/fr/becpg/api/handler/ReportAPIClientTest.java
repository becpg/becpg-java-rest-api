package fr.becpg.api.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import fr.becpg.api.model.RemoteEntity;
import fr.becpg.api.model.RemoteEntityRef;
import fr.becpg.api.model.RemoteNodeInfo;
import fr.becpg.api.model.RemoteReport;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * Choice of the report document among the reports of an entity, and generation of the chosen one, without any repository.
 */
class ReportAPIClientTest {

	private static final String REPOSITORY_URL = "http://repository";

	private static final String CALLER_AUTHORIZATION = "Bearer caller";

	private static final String PDF = "%PDF-1.5 generated report";

	@Test
	void shouldPickTheDocumentOfTheLanguage() {
		List<RemoteReport> reports = List.of(report("r-fr", "t1", "Fiche Coûts.rptdesign", "fr"), report("r-en", "t1", "Fiche Coûts.rptdesign", "en"));

		assertEquals("r-en", ReportAPIClient.selectReport(reports, "fiche coûts", "en").getId());
		assertEquals("r-fr", ReportAPIClient.selectReport(reports, "t1", "fr").getId());
	}

	@Test
	void shouldFindTheDocumentOfTheLanguageFromAFullLocale() {
		List<RemoteReport> reports = List.of(report("r-fr", "t1", "Fiche Coûts.rptdesign", "fr"), report("r-en", "t1", "Fiche Coûts.rptdesign", "en_US"));

		assertEquals("r-fr", ReportAPIClient.selectReport(reports, "Fiche Coûts", "fr_FR").getId());
		assertEquals("r-fr", ReportAPIClient.selectReport(reports, "Fiche Coûts", "fr-FR").getId());
		assertEquals("r-en", ReportAPIClient.selectReport(reports, "Fiche Coûts", "en_us").getId());
	}

	@Test
	void shouldAskForTheLanguageWhenTheTemplateHasSeveralDocuments() {
		List<RemoteReport> reports = List.of(report("r-fr", "t1", "Fiche Coûts.rptdesign", "fr"), report("r-en", "t1", "Fiche Coûts.rptdesign", "en"));

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> ReportAPIClient.selectReport(reports, "Fiche Coûts", null));

		assertTrue(exception.getMessage().contains("or the language"), exception.getMessage());
	}

	@Test
	void shouldAskForTheTemplateIdWhenTwoTemplatesShareAName() {
		// Two templates of the same name, in two folders of the report templates
		List<RemoteReport> reports = List.of(report("r1", "t1", "Fiche Coûts.rptdesign", "fr"), report("r2", "t2", "Fiche Coûts.rptdesign", "fr"));

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> ReportAPIClient.selectReport(reports, "Fiche Coûts", "fr"));

		assertTrue(exception.getMessage().contains("template t1") && exception.getMessage().contains("template t2"), exception.getMessage());
		assertEquals("r2", ReportAPIClient.selectReport(reports, "t2", "fr").getId());
	}

	@Test
	void shouldListTheReportsOfAnEntityWithoutReports() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> ReportAPIClient.selectReport(List.of(), "Fiche Coûts", "fr"));

		assertTrue(exception.getMessage().endsWith("Available reports: none"), exception.getMessage());
	}

	@Test
	void shouldGenerateTheReportWithTheAuthenticationOfTheCallingThread() {
		// Stands for a delegated authentication read from a context bound to the calling thread, as a Spring security context
		ThreadLocal<String> callerAuthorization = new ThreadLocal<>();
		ReportAPIClient client = new ReportAPIClient(entityApiAnsweringOnAnotherThread(reportFields("r-fr", "t1", "Fiche Coûts.rptdesign", "fr")));
		client.webClient = WebClient.builder().baseUrl(REPOSITORY_URL)
				.filter(ExchangeFilterFunction.ofRequestProcessor(request -> Mono.just(authorize(request, callerAuthorization.get()))))
				.exchangeFunction(ReportAPIClientTest::answerPdfToAuthorizedRequests).build();

		callerAuthorization.set(CALLER_AUTHORIZATION);
		try {
			assertEquals(PDF.length(), client.generateReport("entity", "Fiche Coûts", "fr").getSize());
		} finally {
			callerAuthorization.remove();
		}
	}

	private static EntityAPI entityApiAnsweringOnAnotherThread(Map<String, Object> reportFields) {
		RemoteEntity entity = new RemoteEntity();
		entity.setAttributes(Map.of(RemoteReport.ASSOC_REPORTS, List.of(reportFields)));
		EntityAPI entityAPI = mock(EntityAPI.class);
		when(entityAPI.fetchEntity(any(), any(), any(), any()))
				.thenReturn(Mono.fromSupplier(() -> new RemoteEntityRef(entity)).subscribeOn(Schedulers.boundedElastic()));
		return entityAPI;
	}

	private static ClientRequest authorize(ClientRequest request, String authorization) {
		return authorization == null ? request : ClientRequest.from(request).header(HttpHeaders.AUTHORIZATION, authorization).build();
	}

	private static Mono<ClientResponse> answerPdfToAuthorizedRequests(ClientRequest request) {
		boolean authorized = CALLER_AUTHORIZATION.equals(request.headers().getFirst(HttpHeaders.AUTHORIZATION));
		return Mono.just(authorized ? ClientResponse.create(HttpStatus.OK).body(PDF).build() : ClientResponse.create(HttpStatus.UNAUTHORIZED).build());
	}

	private static RemoteReport report(String id, String templateId, String templateName, String locale) {
		return new RemoteReport(new RemoteNodeInfo(reportFields(id, templateId, templateName, locale)));
	}

	private static Map<String, Object> reportFields(String id, String templateId, String templateName, String locale) {
		Map<String, Object> template = Map.of("id", templateId, "cm:name", templateName, "type", "rep:reportTpl");
		Map<String, Object> attributes = Map.of(RemoteReport.ASSOC_REPORT_TPL, template, RemoteReport.PROP_REPORT_LOCALES, List.of(locale),
				RemoteReport.PROP_REPORT_IS_DIRTY, true, RemoteReport.PROP_REPORT_IS_DEFAULT, false);
		return Map.of("id", id, "cm:name", "PF - " + templateName, "type", "rep:report", "attributes", attributes);
	}

}
