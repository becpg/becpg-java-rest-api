package fr.becpg.api.handler;

import java.util.List;

import fr.becpg.api.model.RemoteReport;
import reactor.core.publisher.Mono;

/**
 * <p>ReportAPI interface.</p>
 * Generates the reports (technical sheets, cost sheets...) of an entity and stores them in its Documents folder, where
 * {@link fr.becpg.api.handler.ContentAPI} reads them.
 * <p>
 * The reports are read through the remote API, available from remote API version 3.0 (JSON response). {@link #generateReports} also calls
 * the beCPG <code>generate-report</code> webscript, outside the remote API.
 */
public interface ReportAPI {

	/**
	 * <p>generateReports.</p>
	 * Creates the report documents of the entity, one per template and language. Only the default report is generated: the other documents
	 * hold a placeholder until they are generated with {@link #generateReport}.
	 *
	 * @param id the entity id or nodeRef
	 * @param force <code>true</code> to generate the default report again even when the entity did not change
	 * @return <code>true</code> when the reports were generated, <code>false</code> when they were up to date
	 */
	boolean generateReports(String id, boolean force);

	/**
	 * <p>generateEntityReports.</p>
	 *
	 * @param id the entity id or nodeRef
	 * @param force <code>true</code> to generate the default report again even when the entity did not change
	 * @return <code>true</code> when the reports were generated, <code>false</code> when they were up to date
	 * @see #generateReports(String, boolean)
	 */
	Mono<Boolean> generateEntityReports(String id, boolean force);

	/**
	 * <p>listReports.</p>
	 * Lists the report documents of the entity, without generating anything.
	 *
	 * @param id the entity id or nodeRef
	 * @return the report documents, empty when the reports of the entity were never generated
	 */
	List<RemoteReport> listReports(String id);

	/**
	 * <p>fetchReports.</p>
	 *
	 * @param id the entity id or nodeRef
	 * @return the report documents, empty when the reports of the entity were never generated
	 * @see #listReports(String)
	 */
	Mono<List<RemoteReport>> fetchReports(String id);

	/**
	 * <p>generateReport.</p>
	 * Generates one report of the entity and stores it in its Documents folder. The report documents are created first when the entity has
	 * none. The returned document can then be read with {@link fr.becpg.api.handler.ContentAPI}.
	 *
	 * @param id the entity id or nodeRef
	 * @param template the id or nodeRef of the report template, or its name, with or without <code>.rptdesign</code>, ignoring the case
	 * @param locale the language of the report (<code>fr</code>, <code>en_US</code>; <code>fr_FR</code> finds a document in
	 *        <code>fr</code>), <code>null</code> when the template has one document only
	 * @return the generated report document, with its size
	 * @throws java.lang.IllegalArgumentException when no document, or several, match the template and the language: the message lists
	 *         the available reports
	 * @throws java.lang.IllegalStateException when the repository did not generate the report (archived entity, version, empty file)
	 */
	RemoteReport generateReport(String id, String template, String locale);

	/**
	 * <p>generateEntityReport.</p>
	 * Chains several remote calls: each one after the first starts on the thread that completed the previous one. With an authentication
	 * bound to the calling thread (a delegated authentication read from the Spring security context), use
	 * {@link #generateReport(String, String, String)}, which makes every call from the calling thread.
	 *
	 * @param id the entity id or nodeRef
	 * @param template the id or nodeRef of the report template, or its name, with or without <code>.rptdesign</code>, ignoring the case
	 * @param locale the language of the report, <code>null</code> when the template has one document only
	 * @return the generated report document, with its size
	 * @see #generateReport(String, String, String)
	 */
	Mono<RemoteReport> generateEntityReport(String id, String template, String locale);

}
