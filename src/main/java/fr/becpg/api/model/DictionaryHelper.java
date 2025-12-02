package fr.becpg.api.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class DictionaryHelper {

	private static final TypeHierarchy TYPE_CONTENT = new TypeHierarchy(BeCPGAPIModel.TYPE_CONTENT, null);
	private static final TypeHierarchy TYPE_FOLDER = new TypeHierarchy(BeCPGAPIModel.TYPE_FOLDER, null);
	private static final TypeHierarchy TYPE_ENTITY_V2 = new TypeHierarchy(BeCPGAPIModel.TYPE_ENTITY_V2, TYPE_FOLDER);
	private static final TypeHierarchy TYPE_PRODUCT = new TypeHierarchy(BeCPGAPIModel.TYPE_PRODUCT, TYPE_ENTITY_V2);
	private static final TypeHierarchy TYPE_FINISHED_PRODUCT = new TypeHierarchy(BeCPGAPIModel.TYPE_FINISHED_PRODUCT, TYPE_PRODUCT);
	private static final TypeHierarchy TYPE_SEMI_FINISHED_PRODUCT = new TypeHierarchy(BeCPGAPIModel.TYPE_SEMI_FINISHED_PRODUCT, TYPE_PRODUCT);
	private static final TypeHierarchy TYPE_RAW_MATERIAL = new TypeHierarchy(BeCPGAPIModel.TYPE_RAW_MATERIAL, TYPE_PRODUCT);
	private static final TypeHierarchy TYPE_PROJECT = new TypeHierarchy(BeCPGAPIModel.TYPE_PROJECT, TYPE_ENTITY_V2);
	private static final TypeHierarchy TYPE_SUPPLIER = new TypeHierarchy(BeCPGAPIModel.TYPE_SUPPLIER, TYPE_ENTITY_V2);
	private static final TypeHierarchy TYPE_CLIENT = new TypeHierarchy(BeCPGAPIModel.TYPE_SUPPLIER, TYPE_ENTITY_V2);

	private static final Map<String, TypeHierarchy> TYPE_REGISTRY = new HashMap<>();
	
	static {
		TYPE_REGISTRY.put(BeCPGAPIModel.TYPE_CONTENT, TYPE_CONTENT);
		TYPE_REGISTRY.put(BeCPGAPIModel.TYPE_FOLDER, TYPE_FOLDER);
		TYPE_REGISTRY.put(BeCPGAPIModel.TYPE_ENTITY_V2, TYPE_ENTITY_V2);
		TYPE_REGISTRY.put(BeCPGAPIModel.TYPE_PRODUCT, TYPE_PRODUCT);
		TYPE_REGISTRY.put(BeCPGAPIModel.TYPE_FINISHED_PRODUCT, TYPE_FINISHED_PRODUCT);
		TYPE_REGISTRY.put(BeCPGAPIModel.TYPE_SEMI_FINISHED_PRODUCT, TYPE_SEMI_FINISHED_PRODUCT);
		TYPE_REGISTRY.put(BeCPGAPIModel.TYPE_RAW_MATERIAL, TYPE_RAW_MATERIAL);
		TYPE_REGISTRY.put(BeCPGAPIModel.TYPE_PROJECT, TYPE_PROJECT);
		TYPE_REGISTRY.put(BeCPGAPIModel.TYPE_SUPPLIER, TYPE_SUPPLIER);
		TYPE_REGISTRY.put(BeCPGAPIModel.TYPE_CLIENT, TYPE_CLIENT);
	}

	/**
	 * Checks if a type is a subtype of a parent type in the hierarchy.
	 * 
	 * @param type the type to check
	 * @param parentType the potential parent type
	 * @return true if type is a subtype of parentType or equal to it, false otherwise
	 */
	public static boolean isSubType(String type, String parentType) {
		if (type == null || parentType == null) {
			return false;
		}
		
		if (type.equals(parentType)) {
			return true;
		}
		
		TypeHierarchy typeHierarchy = TYPE_REGISTRY.get(type);
		if (typeHierarchy == null) {
			return false;
		}
		
		return typeHierarchy.isSubTypeOf(parentType);
	}

	public static class TypeHierarchy {
		private String type;
		private TypeHierarchy parent;

		public TypeHierarchy(String type, TypeHierarchy parent) {
			super();
			this.type = type;
			this.parent = parent;
		}

		/**
		 * Checks if this type is a subtype of the given parent type.
		 * 
		 * @param parentType the parent type to check against
		 * @return true if this type inherits from parentType, false otherwise
		 */
		public boolean isSubTypeOf(String parentType) {
			if (type.equals(parentType)) {
				return true;
			}
			
			if (parent == null) {
				return false;
			}
			return parent.isSubTypeOf(parentType);
		}
		

		public String getType() {
			return type;
		}

		public TypeHierarchy getParent() {
			return parent;
		}
	}

	public static List<String> getSubTypesOf(String parentType, boolean includeAllHierarchy) {
		List<String> subTypes = new ArrayList<>();
		for (Entry<String, TypeHierarchy> entry : TYPE_REGISTRY.entrySet()) {
			if (entry.getValue().parent != null && entry.getValue().parent.type.equals(parentType)) {
				subTypes.add(entry.getKey());
				if (includeAllHierarchy) {
					subTypes.addAll(getSubTypesOf(entry.getKey(), true));
				}
			}
		}
		return subTypes;
	}
}