package fr.becpg.api.model;

import java.util.List;
import java.util.Map;

/**
 * One page of the beCPG audit.
 *
 * @param entries the entries in the order asked for, never <code>null</code>; each one holds its <code>id</code> plus the keys of its audit type
 * @param nextStartAfterId the cursor of the next page, <code>null</code> on the last page and before beCPG 26.1.0.42
 * @param scanInterrupted whether the scan stopped before filling the page, so that further entries may match; <code>null</code> before beCPG
 *                        26.1.0.42
 */
public record RemoteAuditPage(List<Map<String, Object>> entries, Long nextStartAfterId, Boolean scanInterrupted) {
}
