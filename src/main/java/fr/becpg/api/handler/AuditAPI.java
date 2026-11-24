package fr.becpg.api.handler;

import fr.becpg.api.model.RemoteAPIException;
import fr.becpg.api.model.RemoteAuditPage;
import fr.becpg.api.model.RemoteAuditQuery;

/**
 * Reads the beCPG audit through the <code>/becpg/stats/{type}</code> webscript, outside the remote API, with the identity of the web client.
 * <p>
 * Cursor pagination is available from beCPG 26.1.0.42, repeatable filters and date range from 26.1.0.49. From beCPG 26.2.0 the webscript is
 * reserved to the administrators.
 */
public interface AuditAPI {

	/**
	 * Reads one page of the audit.
	 *
	 * @param query what to read
	 * @return the page
	 * @throws IllegalArgumentException when the type is not a plain name, before any call
	 * @throws RemoteAPIException with status 403 when the caller is refused (beCPG 26.2.0 and later), with status 400 and
	 *                                 the repository message when a date, the type or a filter key is invalid
	 */
	RemoteAuditPage listEntries(RemoteAuditQuery query);

}
