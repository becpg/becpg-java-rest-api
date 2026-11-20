package fr.becpg.api.model;

import java.util.Collections;
import java.util.List;

import org.jspecify.annotations.Nullable;

/**
 * <p>RemoteReport class.</p>
 * A report document of an entity (technical sheet, cost sheet...), stored in the Documents folder of the entity: one document per report
 * template and language.
 */
public class RemoteReport {

	/** Constant <code>ASSOC_REPORTS="rep:reports"</code> */
	public static final String ASSOC_REPORTS = "rep:reports";
	/** Constant <code>ASSOC_REPORT_TPL="rep:reportTplAssoc"</code> */
	public static final String ASSOC_REPORT_TPL = "rep:reportTplAssoc";
	/** Constant <code>PROP_REPORT_LOCALES="rep:reportLocales"</code> */
	public static final String PROP_REPORT_LOCALES = "rep:reportLocales";
	/** Constant <code>PROP_REPORT_IS_DEFAULT="rep:reportIsDefault"</code> */
	public static final String PROP_REPORT_IS_DEFAULT = "rep:reportIsDefault";
	/** Constant <code>PROP_REPORT_IS_DIRTY="rep:reportIsDirty"</code> */
	public static final String PROP_REPORT_IS_DIRTY = "rep:reportIsDirty";

	private static final String NODEREF_PREFIX = "workspace://SpacesStore/";

	private final String id;
	private final String name;
	private final String templateId;
	private final String templateName;
	private final List<String> locales;
	private final Boolean isDefault;
	private final Boolean isDirty;
	private final Long size;

	/**
	 * Builds a report from a target of the <code>rep:reports</code> association of an entity.
	 *
	 * @param node a {@link fr.becpg.api.model.RemoteNodeInfo} object
	 */
	public RemoteReport(RemoteNodeInfo node) {
		this(node, null);
	}

	private RemoteReport(RemoteNodeInfo node, Long size) {
		RemoteNodeInfo template = node.getAssociation(ASSOC_REPORT_TPL);
		List<String> nodeLocales = node.getStringArrayProp(PROP_REPORT_LOCALES);
		this.id = node.getId();
		this.name = node.getName() != null ? node.getName() : node.getStringProp("cm:name");
		this.templateId = template != null ? template.getId() : null;
		this.templateName = template != null ? template.getName() : null;
		this.locales = nodeLocales != null ? List.copyOf(nodeLocales) : Collections.emptyList();
		this.isDefault = node.getBooleanProp(PROP_REPORT_IS_DEFAULT);
		this.isDirty = node.getBooleanProp(PROP_REPORT_IS_DIRTY);
		this.size = size;
	}

	private RemoteReport(RemoteReport report, long size) {
		this.id = report.id;
		this.name = report.name;
		this.templateId = report.templateId;
		this.templateName = report.templateName;
		this.locales = report.locales;
		this.isDefault = report.isDefault;
		this.isDirty = Boolean.FALSE;
		this.size = size;
	}

	/**
	 * <p>withGeneratedSize.</p>
	 *
	 * @param generatedSize the size of the generated file, in bytes
	 * @return the same report, generated, with its size
	 */
	public RemoteReport withGeneratedSize(long generatedSize) {
		return new RemoteReport(this, generatedSize);
	}

	/**
	 * <p>getId.</p>
	 *
	 * @return the id of the report document
	 */
	public String getId() {
		return id;
	}

	/**
	 * <p>getNodeRef.</p>
	 *
	 * @return the nodeRef of the report document
	 */
	public String getNodeRef() {
		return id != null ? NODEREF_PREFIX + id : null;
	}

	/**
	 * <p>getName.</p>
	 *
	 * @return the file name of the report document
	 */
	public String getName() {
		return name;
	}

	/**
	 * <p>getTemplateId.</p>
	 *
	 * @return the id of the report template
	 */
	@Nullable
	public String getTemplateId() {
		return templateId;
	}

	/**
	 * <p>getTemplateName.</p>
	 *
	 * @return the file name of the report template, e.g. <code>Fiche Coûts.rptdesign</code>
	 */
	@Nullable
	public String getTemplateName() {
		return templateName;
	}

	/**
	 * <p>getLocales.</p>
	 *
	 * @return the languages of the report document
	 */
	public List<String> getLocales() {
		return locales;
	}

	/**
	 * <p>isDefault.</p>
	 *
	 * @return <code>true</code> for the report selected by default on the entity
	 */
	@Nullable
	public Boolean isDefault() {
		return isDefault;
	}

	/**
	 * <p>isDirty.</p>
	 *
	 * @return <code>true</code> when the document only holds a placeholder and is generated at its next reading
	 */
	@Nullable
	public Boolean isDirty() {
		return isDirty;
	}

	/**
	 * <p>getSize.</p>
	 *
	 * @return the size of the generated file in bytes, only set by {@link fr.becpg.api.handler.ReportAPI#generateReport}
	 */
	@Nullable
	public Long getSize() {
		return size;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return (templateName != null ? templateName : name) + " " + locales;
	}

}
