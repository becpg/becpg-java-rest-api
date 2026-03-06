package fr.becpg.api.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.becpg.api.model.BeCPGAPIModel;
import fr.becpg.api.model.RemoteEntity;
import fr.becpg.api.model.RemoteNodeInfo;

/**
 * <p>RemoteEntityHelper class.</p>
 *
 * @author matthieu
 */
public class RemoteEntityHelper {

	private static final String ID = "id";
	private static final String ATTRIBUTES = "attributes";

	private RemoteEntityHelper() {

	}

	/**
	 * <p>findDocumentAttributes.</p>
	 *
	 * @param attributes a {@link java.util.Map} object
	 * @param docId a {@link java.lang.String} object
	 * @return a {@link java.util.Map} object
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> findDocumentAttributes(Map<String, Object> attributes, String docId) {
		List<Map<String, Object>> documents = (List<Map<String, Object>>) attributes.get(BeCPGAPIModel.ASSOC_CM_CONTAINS);
		if (documents != null) {
			for (Map<String, Object> document : documents) {
				if (docId.equals(document.get(ID))) {
					return (Map<String, Object>) document.get(ATTRIBUTES);
				}
				if (document.get(ATTRIBUTES) != null) {
					Map<String, Object> subAttributes = findDocumentAttributes((Map<String, Object>) document.get(ATTRIBUTES), docId);
					if (subAttributes != null) {
						return subAttributes;
					}
				}
			}
		}
		return null;
	}

	/**
	 * <p>insertDocument.</p>
	 *
	 * @param original a {@link fr.becpg.api.model.RemoteEntity} object
	 * @param target a {@link fr.becpg.api.model.RemoteEntity} object
	 * @param docId a {@link java.lang.String} object
	 */
	@SuppressWarnings("unchecked")
	public static void insertDocument(RemoteEntity original, RemoteEntity target, String docId) {
		Map<String, Object> originalAttributes = original.getAttributes();
		if ((originalAttributes != null) && (originalAttributes.get(BeCPGAPIModel.ASSOC_CM_CONTAINS) != null)) {
			List<Map<String, Object>> originalDocuments = (List<Map<String, Object>>) originalAttributes.get(BeCPGAPIModel.ASSOC_CM_CONTAINS);
			for (Map<String, Object> rootDocument : originalDocuments) {
				Map<String, Object> foundDocWrapper = findDocumentWrapper(rootDocument, docId);
				if (foundDocWrapper != null) {
					Map<String, Object> rootDocWrapper = new HashMap<>();
					rootDocWrapper.put(BeCPGAPIModel.PROP_CM_NAME, rootDocument.get(BeCPGAPIModel.PROP_CM_NAME));
					Map<String, Object> rootDocWrapperAttributes = new HashMap<>();
					rootDocWrapperAttributes.put(BeCPGAPIModel.ASSOC_CM_CONTAINS, List.of(foundDocWrapper));
					rootDocWrapper.put(ATTRIBUTES, rootDocWrapperAttributes);
					Map<String, Object> targetAttributes = new HashMap<>();
					targetAttributes.put(BeCPGAPIModel.ASSOC_CM_CONTAINS, List.of(rootDocWrapper));
					target.setAttributes(targetAttributes);
					return;
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private static Map<String, Object> findDocumentWrapper(Map<String, Object> document, String docId) {
		if (docId.equals(document.get(ID))) {
			return document;
		}
		Map<String, Object> attrs = (Map<String, Object>) document.get(ATTRIBUTES);
		if ((attrs != null) && (attrs.get(BeCPGAPIModel.ASSOC_CM_CONTAINS) != null)) {
			List<Map<String, Object>> subDocs = (List<Map<String, Object>>) attrs.get(BeCPGAPIModel.ASSOC_CM_CONTAINS);
			for (Map<String, Object> subDoc : subDocs) {
				Map<String, Object> match = findDocumentWrapper(subDoc, docId);
				if (match != null) {
					Map<String, Object> wrapper = new HashMap<>();
					Map<String, Object> newAttrs = new HashMap<>();
					wrapper.put(ATTRIBUTES, newAttrs);
					String id = (String) subDoc.get(ID);
					if (docId.equals(id)) {
						wrapper.put(ID, id);
					} else {
						newAttrs.put(BeCPGAPIModel.ASSOC_CM_CONTAINS, List.of(match));
						wrapper.put(BeCPGAPIModel.PROP_CM_NAME, subDoc.get(BeCPGAPIModel.PROP_CM_NAME));
					}
					return wrapper;
				}
			}
		}
		return null;
	}

	/**
	 * <p>extractName.</p>
	 *
	 * @param entity a {@link fr.becpg.api.model.RemoteNodeInfo} object
	 * @return a {@link java.lang.String} object
	 */
	public static String extractName(RemoteNodeInfo entity) {
		String ret = entity.getStringProp(BeCPGAPIModel.PROP_CHARACT_NAME);
		if (ret == null) {
			Map<String, Object> optionalIdentifiers = entity.getOptionalIdentifiers();
			if (optionalIdentifiers != null) {
				ret = (String) optionalIdentifiers.get(BeCPGAPIModel.PROP_CHARACT_NAME);
			}
		}
		if (ret == null) {
			ret = entity.getName();
		}
		return ret;
	}

	/**
	 * Deep copy method for RemoteEntity class.
	 *
	 * @param from the RemoteEntity to copy
	 * @return a new RemoteEntity instance with all fields deeply copied
	 */
	public static RemoteEntity deepCopy(RemoteEntity from) {
		if (from == null) {
			return null;
		}
		RemoteEntity copy = new RemoteEntity();
		copyCommonFields(from, copy);

		Map<String, List<RemoteNodeInfo>> datalists = from.getDatalists();
		
		if (datalists != null) {
			Map<String, List<RemoteNodeInfo>> datalistsCopy = new HashMap<>();
			for (Map.Entry<String, List<RemoteNodeInfo>> entry : datalists.entrySet()) {
				List<RemoteNodeInfo> listCopy = new ArrayList<>();
				if (entry.getValue() != null) {
					for (RemoteNodeInfo node : entry.getValue()) {
						listCopy.add(deepCopy(node));
					}
				}
				datalistsCopy.put(entry.getKey(), listCopy);
			}
			copy.setDatalists(datalistsCopy);
		}

		
		Map<String, Object> attributes = from.getParams();
		if (attributes != null) {
			copy.setParams(deepCopyMap(attributes));
		}

		return copy;
	}

	/**
	 * Helper method to deep copy RemoteNodeInfo objects.
	 *
	 * @param original the RemoteNodeInfo to copy
	 * @return a new RemoteNodeInfo instance
	 */
	public static RemoteNodeInfo deepCopy(RemoteNodeInfo original) {
		if (original == null) {
			return null;
		}

		if (original instanceof RemoteEntity originalEntity) {
			return deepCopy(originalEntity);
		}

		RemoteNodeInfo copy = new RemoteNodeInfo();
		copyCommonFields(original, copy);
		return copy;
	}

	private static void copyCommonFields(RemoteNodeInfo from, RemoteNodeInfo to) {
		to.setParent(from.getParent());
		to.setId(from.getId());
		to.setName(from.getName());
		to.setCode(from.getCode());
		to.setErpCode(from.getErpCode());
		to.setSite(from.getSite());
		to.setPath(from.getPath());
		to.setType(from.getType());
		to.setContent(from.getContent());

		Map<String, Object> attributes = from.getAttributes();
		if (attributes != null) {
		    to.setAttributes(deepCopyMap(attributes));
		}

		if (from.getOptionalIdentifiers() != null) {
			to.setOptionalIdentifiers(deepCopyMap(from.getOptionalIdentifiers()));
		}
	}

	private static Map<String, Object> deepCopyMap(Map<String, Object> from) {
		Map<String, Object> copy = new HashMap<>();
		for (Map.Entry<String, Object> entry : from.entrySet()) {
			copy.put(entry.getKey(), deepCopyValue(entry.getValue()));
		}
		return copy;
	}

	/**
	 * Helper method to deep copy generic values.
	 * Handles primitives, strings, lists, maps, and RemoteNodeInfo objects.
	 *
	 * @param value the value to copy
	 * @return a deep copy of the value
	 */
	private static Object deepCopyValue(Object value) {
		if (value == null) {
			return null;
		}

		if ((value instanceof String) || (value instanceof Number) || (value instanceof Boolean)) {
			return value;
		}

		if (value instanceof Date date) {
			return new Date(date.getTime());
		}

		if (value instanceof RemoteNodeInfo remoteNodeInfo) {
			return deepCopy(remoteNodeInfo);
		}

		if (value instanceof List<?> originalList) {
			List<Object> listCopy = new ArrayList<>();
			for (Object item : originalList) {
				listCopy.add(deepCopyValue(item));
			}
			return listCopy;
		}

		if (value instanceof Map<?, ?> originalMap) {
			Map<Object, Object> mapCopy = new HashMap<>();
			for (Map.Entry<?, ?> entry : originalMap.entrySet()) {
				mapCopy.put(deepCopyValue(entry.getKey()), deepCopyValue(entry.getValue()));
			}
			return mapCopy;
		}

		return value;
	}
}
