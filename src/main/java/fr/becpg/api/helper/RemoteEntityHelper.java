package fr.becpg.api.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.becpg.api.model.BeCPGAPIModel;
import fr.becpg.api.model.RemoteEntity;
import fr.becpg.api.model.RemoteNodeInfo;

public class RemoteEntityHelper {
	
	private static final String ID = "id";
	private static final String ATTRIBUTES = "attributes";

	private RemoteEntityHelper() {
		
	}
	
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
	
	@SuppressWarnings("unchecked")
	public static void insertDocument(RemoteEntity original, RemoteEntity target, String docId) {
		Map<String, Object> originalAttributes = original.getAttributes();
		if (originalAttributes != null && originalAttributes.get(BeCPGAPIModel.ASSOC_CM_CONTAINS) != null) {
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
		if (attrs != null && attrs.get(BeCPGAPIModel.ASSOC_CM_CONTAINS) != null) {
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
	 * Add this method to the RemoteEntity class.
	 */

	/**
	 * Creates a deep copy of this RemoteEntity.
	 * 
	 * @return a new RemoteEntity instance with all fields deeply copied
	 */
	public static RemoteEntity deepCopy(RemoteEntity from) {
	    RemoteEntity copy = new RemoteEntity();
	    
	    // Copy fields from RemoteNodeInfo (parent class)
	    copy.setParent(from.getParent());
	    copy.setId(from.getId());
	    copy.setName(from.getName());
	    copy.setCode(from.getCode());
	    copy.setErpCode(from.getErpCode());
	    copy.setSite(from.getSite()); // Note: RemoteSiteInfo is not deeply copied - consider if needed
	    copy.setPath(from.getPath());
	    copy.setType(from.getType());
	    copy.setContent(from.getContent());
	    
	    // Deep copy attributes map
	    if (from.getAttributes() != null) {
	        Map<String, Object> attributesCopy = new HashMap<>();
	        for (Map.Entry<String, Object> entry : from.getAttributes().entrySet()) {
	            attributesCopy.put(entry.getKey(), deepCopyValue(entry.getValue()));
	        }
	        copy.setAttributes(attributesCopy);
	    }
	    
	    // Deep copy optionalIdentifiers map
	    if (from.getOptionalIdentifiers() != null) {
	        Map<String, Object> optionalIdentifiersCopy = new HashMap<>();
	        for (Map.Entry<String, Object> entry : from.getOptionalIdentifiers().entrySet()) {
	            optionalIdentifiersCopy.put(entry.getKey(), deepCopyValue(entry.getValue()));
	        }
	        copy.setOptionalIdentifiers(optionalIdentifiersCopy);
	    }
	    
	    // Deep copy datalists
	    if (from.getDatalists() != null) {
	        Map<String, List<RemoteNodeInfo>> datalistsCopy = new HashMap<>();
	        for (Map.Entry<String, List<RemoteNodeInfo>> entry : from.getDatalists().entrySet()) {
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
	    
	    // Deep copy params
	    if (from.getParams() != null) {
	        Map<String, Object> paramsCopy = new HashMap<>();
	        for (Map.Entry<String, Object> entry : from.getParams().entrySet()) {
	            paramsCopy.put(entry.getKey(), deepCopyValue(entry.getValue()));
	        }
	        copy.setParams(paramsCopy);
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
	    
	    // If it's actually a RemoteEntity, use its deep copy method
	    if (original instanceof RemoteEntity originalEntity) {
	        return deepCopy(originalEntity);
	    }
	    
	    RemoteNodeInfo copy = new RemoteNodeInfo();
	    copy.setParent(original.getParent());
	    copy.setId(original.getId());
	    copy.setName(original.getName());
	    copy.setCode(original.getCode());
	    copy.setErpCode(original.getErpCode());
	    copy.setSite(original.getSite()); // Note: RemoteSiteInfo is not deeply copied
	    copy.setPath(original.getPath());
	    copy.setType(original.getType());
	    copy.setContent(original.getContent());
	    
	    // Deep copy attributes
	    if (original.getAttributes() != null) {
	        Map<String, Object> attributesCopy = new HashMap<>();
	        for (Map.Entry<String, Object> entry : original.getAttributes().entrySet()) {
	            attributesCopy.put(entry.getKey(), deepCopyValue(entry.getValue()));
	        }
	        copy.setAttributes(attributesCopy);
	    }
	    
	    // Deep copy optionalIdentifiers
	    if (original.getOptionalIdentifiers() != null) {
	        Map<String, Object> optionalIdentifiersCopy = new HashMap<>();
	        for (Map.Entry<String, Object> entry : original.getOptionalIdentifiers().entrySet()) {
	            optionalIdentifiersCopy.put(entry.getKey(), deepCopyValue(entry.getValue()));
	        }
	        copy.setOptionalIdentifiers(optionalIdentifiersCopy);
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
	    
	    // Immutable types - can return as-is
	    if (value instanceof String || value instanceof Number || 
	        value instanceof Boolean || value instanceof Date) {
	        // For Date objects, create a new instance to ensure true deep copy
	        if (value instanceof Date date) {
	            return new Date(date.getTime());
	        }
	        return value;
	    }
	    
	    // Handle RemoteNodeInfo and its subclasses
	    if (value instanceof RemoteNodeInfo remoteNodeInfo) {
	        return deepCopy(remoteNodeInfo);
	    }
	    
	    // Handle Lists
	    if (value instanceof List) {
	        List<?> originalList = (List<?>) value;
	        List<Object> listCopy = new ArrayList<>();
	        for (Object item : originalList) {
	            listCopy.add(deepCopyValue(item));
	        }
	        return listCopy;
	    }
	    
	    // Handle Maps
	    if (value instanceof Map) {
	        Map<?, ?> originalMap = (Map<?, ?>) value;
	        Map<Object, Object> mapCopy = new HashMap<>();
	        for (Map.Entry<?, ?> entry : originalMap.entrySet()) {
	            mapCopy.put(deepCopyValue(entry.getKey()), deepCopyValue(entry.getValue()));
	        }
	        return mapCopy;
	    }
	    
	    // For other types, return as-is (you may want to add more specific handling)
	    return value;
	}
}
