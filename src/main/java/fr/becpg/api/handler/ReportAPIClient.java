package fr.becpg.api.handler;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import fr.becpg.api.model.RemoteReport;
import fr.becpg.api.security.EnableAuthConfiguration;
import reactor.core.publisher.Mono;

/**
 * <p>ReportAPIClient class.</p>
 */
@Component
@EnableAuthConfiguration
public class ReportAPIClient extends AbstractAPIClient implements ReportAPI {

	private static final String REMOTE_CONTENT_URL = "/entity/content";

	private static final String TEMPLATE_EXTENSION = ".rptdesign";

	private static final String NODEREF_SEPARATOR = "://";

	/** What the repository writes in a report document that is generated at its next reading */
	private static final String PLACEHOLDER = "Loading ...";

	private static final int PLACEHOLDER_PROBE_LENGTH = 32;

	/** A datalist no entity has: an empty <code>lists</code> parameter would export every datalist of the entity */
	private static final String NO_DATALIST = "none";

	private static final List<String> REPORT_FIELDS = List.of(RemoteReport.ASSOC_REPORTS + "|cm:name",
			RemoteReport.ASSOC_REPORTS + "|" + RemoteReport.PROP_REPORT_LOCALES, RemoteReport.ASSOC_REPORTS + "|" + RemoteReport.ASSOC_REPORT_TPL,
			RemoteReport.ASSOC_REPORTS + "|" + RemoteReport.PROP_REPORT_IS_DIRTY,
			RemoteReport.ASSOC_REPORTS + "|" + RemoteReport.PROP_REPORT_IS_DEFAULT);

	private final EntityAPI entityAPI;

	/**
	 * <p>Constructor for ReportAPIClient.</p>
	 *
	 * @param entityAPI the client reading the report documents of the entity
	 */
	public ReportAPIClient(EntityAPI entityAPI) {
		this.entityAPI = entityAPI;
	}

	/** {@inheritDoc} */
	@Override
	public boolean generateReports(String id, boolean force) {
		return Boolean.TRUE.equals(generateEntityReports(id, force).block());
	}

	/** {@inheritDoc} */
	@Override
	public Mono<Boolean> generateEntityReports(String id, boolean force) {
		if (id == null) {
			return Mono.error(new NullPointerException("id is mandatory"));
		}
		// Deferred, so that a malformed id reaches the caller through the Mono
		return Mono.defer(() -> webClient().get().uri(generateReportsUri(id, force)).accept(MediaType.APPLICATION_JSON).retrieve()
				.onStatus(HttpStatusCode::isError, response -> handleErrorResponse(response))
				.bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
				}).map(response -> Boolean.TRUE.equals(response.get("generateReport"))));
	}

	/**
	 * The generate-report webscript is not part of the remote API: it is reached from the content service URL, through the same
	 * authenticated client.
	 */
	private URI generateReportsUri(String id, boolean force) {
		String[] nodeRef = buildNodeRefParam(id).split(NODEREF_SEPARATOR);
		String[] store = nodeRef.length == 2 ? nodeRef[1].split("/") : new String[0];
		if (store.length != 2) {
			throw new IllegalArgumentException("Not an entity id or nodeRef: " + id);
		}
		return UriComponentsBuilder.fromUriString(apiConfiguration.getContentServiceUrl().replaceAll("/+$", ""))
				.pathSegment("alfresco", "service", "becpg", "entity", "generate-report", "node", nodeRef[0], store[0], store[1],
						force ? "force" : "check")
				.build().encode().toUri();
	}

	/** {@inheritDoc} */
	@Override
	public List<RemoteReport> listReports(String id) {
		List<RemoteReport> reports = fetchReports(id).block();
		return reports != null ? reports : Collections.emptyList();
	}

	/** {@inheritDoc} */
	@Override
	public Mono<List<RemoteReport>> fetchReports(String id) {
		if (id == null) {
			return Mono.error(new NullPointerException("id is mandatory"));
		}
		return entityAPI.fetchEntity(id, REPORT_FIELDS, List.of(NO_DATALIST), null)
				.map(entityRef -> entityRef.getEntity() != null
						? entityRef.getEntity().getAssociations(RemoteReport.ASSOC_REPORTS).stream().map(RemoteReport::new).toList()
						: List.<RemoteReport>of())
				.defaultIfEmpty(List.of());
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Each remote call is made and awaited from the calling thread. Chained in one reactive pipeline, as {@link #generateEntityReport} does, a call
	 * starts on the thread that completed the previous one, where an authentication bound to the calling thread (a delegated authentication read
	 * from the Spring security context) is missing: the repository would refuse it.
	 */
	@Override
	public RemoteReport generateReport(String id, String template, String locale) {
		Objects.requireNonNull(id, "id is mandatory");
		Objects.requireNonNull(template, "template is mandatory");
		List<RemoteReport> reports = listReports(id);
		if (reports.isEmpty()) {
			generateReports(id, false);
			reports = listReports(id);
		}
		RemoteReport report = selectReport(reports, template, locale);
		Long size = generateContent(report).block();
		return report.withGeneratedSize(size);
	}

	/** {@inheritDoc} */
	@Override
	public Mono<RemoteReport> generateEntityReport(String id, String template, String locale) {
		if (id == null) {
			return Mono.error(new NullPointerException("id is mandatory"));
		}
		if (template == null) {
			return Mono.error(new NullPointerException("template is mandatory"));
		}
		return fetchReports(id)
				.flatMap(reports -> reports.isEmpty() ? generateEntityReports(id, false).then(fetchReports(id)) : Mono.just(reports))
				.map(reports -> selectReport(reports, template, locale))
				.flatMap(report -> generateContent(report).map(report::withGeneratedSize));
	}

	/**
	 * Reads the content of the report document, which makes the repository generate it when it is out of date, and counts its bytes
	 * without keeping them.
	 */
	private Mono<Long> generateContent(RemoteReport report) {
		return webClient().get()
				.uri(uriBuilder -> uriBuilder.path(REMOTE_CONTENT_URL).queryParam(PARAM_NODEREF, report.getNodeRef()).build()).retrieve()
				.onStatus(HttpStatusCode::isError, response -> handleErrorResponse(response))
				.bodyToFlux(DataBuffer.class)
				.reduceWith(ContentProbe::new, (probe, buffer) -> {
					try {
						probe.read(buffer);
					} finally {
						DataBufferUtils.release(buffer);
					}
					return probe;
				})
				.switchIfEmpty(Mono.fromSupplier(ContentProbe::new))
				.<Long>handle((probe, sink) -> {
					if (probe.isPlaceholder() || (probe.size == 0)) {
						sink.error(new IllegalStateException(
								"Report not generated by the repository (archived entity or version?): " + report.getName()));
					} else {
						sink.next(probe.size);
					}
				});
	}

	/**
	 * Picks the document of the template and the language among the reports of the entity.
	 */
	static RemoteReport selectReport(List<RemoteReport> reports, String template, String locale) {
		List<RemoteReport> ofTemplate = reports.stream().filter(report -> matchesTemplate(report, template)).toList();
		if (ofTemplate.isEmpty()) {
			throw new IllegalArgumentException("No report template '" + template + "' on this entity. Available reports: " + describe(reports));
		}

		List<RemoteReport> candidates = ofTemplate;
		if (locale != null) {
			candidates = ofTemplate.stream().filter(report -> report.getLocales().stream().anyMatch(locale::equalsIgnoreCase)).toList();
			if (candidates.isEmpty()) {
				// fr_FR or en-US finds the document stored under its language alone
				String language = locale.split("[_-]")[0];
				candidates = ofTemplate.stream().filter(report -> report.getLocales().stream().anyMatch(language::equalsIgnoreCase)).toList();
			}
			if (candidates.isEmpty()) {
				throw new IllegalArgumentException(
						"No report '" + template + "' in language '" + locale + "' on this entity. Available reports: " + describe(ofTemplate));
			}
		}

		if (candidates.size() > 1) {
			throw new IllegalArgumentException("Several reports match '" + template + "'" + (locale != null ? " in language '" + locale + "'" : "")
					+ ", give the template id" + (locale == null ? " or the language" : "") + ": "
					+ candidates.stream().map(report -> report + " (template " + report.getTemplateId() + ")").collect(Collectors.joining(", ")));
		}
		return candidates.get(0);
	}

	private static boolean matchesTemplate(RemoteReport report, String template) {
		String expected = template.trim();
		int separator = expected.lastIndexOf('/');
		if (expected.contains(NODEREF_SEPARATOR) && (separator != -1)) {
			expected = expected.substring(separator + 1);
		}
		if (expected.equalsIgnoreCase(report.getTemplateId())) {
			return true;
		}
		return (report.getTemplateName() != null) && withoutExtension(expected).equalsIgnoreCase(withoutExtension(report.getTemplateName()));
	}

	private static String withoutExtension(String templateName) {
		return templateName.toLowerCase(Locale.ROOT).endsWith(TEMPLATE_EXTENSION)
				? templateName.substring(0, templateName.length() - TEMPLATE_EXTENSION.length())
				: templateName;
	}

	private static String describe(List<RemoteReport> reports) {
		return reports.isEmpty() ? "none" : reports.stream().map(RemoteReport::toString).collect(Collectors.joining(", "));
	}

	/**
	 * The size of the content and its first bytes, enough to recognize the placeholder of a report that was not generated.
	 */
	private static final class ContentProbe {

		private long size;

		private final ByteArrayOutputStream head = new ByteArrayOutputStream();

		private void read(DataBuffer buffer) {
			int length = buffer.readableByteCount();
			if (head.size() < PLACEHOLDER_PROBE_LENGTH) {
				byte[] bytes = new byte[Math.min(length, PLACEHOLDER_PROBE_LENGTH - head.size())];
				buffer.read(bytes);
				head.writeBytes(bytes);
			}
			size += length;
		}

		private boolean isPlaceholder() {
			return (size < PLACEHOLDER_PROBE_LENGTH) && PLACEHOLDER.equals(head.toString(StandardCharsets.UTF_8).trim());
		}
	}

}
