package fr.becpg.api.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import fr.becpg.api.model.RemoteNodeInfo;
import fr.becpg.api.model.RemoteReport;

/**
 * Choice of the report document among the reports of an entity, without any repository.
 */
class ReportAPIClientTest {

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

	private static RemoteReport report(String id, String templateId, String templateName, String locale) {
		Map<String, Object> template = Map.of("id", templateId, "cm:name", templateName, "type", "rep:reportTpl");
		Map<String, Object> attributes = Map.of(RemoteReport.ASSOC_REPORT_TPL, template, RemoteReport.PROP_REPORT_LOCALES, List.of(locale),
				RemoteReport.PROP_REPORT_IS_DIRTY, true, RemoteReport.PROP_REPORT_IS_DEFAULT, false);
		return new RemoteReport(new RemoteNodeInfo(Map.of("id", id, "cm:name", "PF - " + templateName, "type", "rep:report", "attributes", attributes)));
	}

}
