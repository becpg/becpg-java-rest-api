package fr.becpg.api.model;

import java.util.List;

/**
 * What to read of the beCPG audit.
 *
 * @param type the audit type, case-insensitive (<code>batch</code>, <code>formulation</code>, <code>activity</code>, <code>report</code>,
 *             <code>export_search</code>), mandatory, a plain name made of letters and underscores: any other value is
 *             refused with an <code>IllegalArgumentException</code> before any call
 * @param filters the <code>key=value</code> filters, all of which must match, <code>null</code> for none
 * @param fromDate the inclusive lower bound, ISO 8601, <code>null</code> when open
 * @param toDate the inclusive upper bound, ISO 8601, <code>null</code> when open
 * @param maxResults the page size, <code>null</code> for the repository default
 * @param startAfterId the cursor of the previous page, <code>null</code> for the first page
 * @param newestFirst <code>true</code> to read the newest entries first
 */
public record RemoteAuditQuery(String type, List<String> filters, String fromDate, String toDate, Integer maxResults, Long startAfterId,
		boolean newestFirst) {
}
