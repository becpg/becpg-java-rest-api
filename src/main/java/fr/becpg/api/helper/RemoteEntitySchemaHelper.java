package fr.becpg.api.helper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.becpg.api.model.RemoteEntitySchema;

/**
 * Helper class for RemoteEntitySchema
 */
public class RemoteEntitySchemaHelper {

	private static final Logger log = LoggerFactory.getLogger(RemoteEntitySchemaHelper.class);

	/** Schema attribute whose title lists every type an association accepts, comma-separated. */
	private static final String ATTR_TARGET_TYPES = "assoc:targetTypes";

	private RemoteEntitySchemaHelper() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Helper method aimed to find recursively a given property in a {@link fr.becpg.api.model.RemoteEntitySchema}
	 *
	 * @param entitySchema a {@link fr.becpg.api.model.RemoteEntitySchema} object
	 * @param prop a {@link java.lang.String} object
	 * @return the {@link fr.becpg.api.model.RemoteEntitySchema} of the property we need or null if the property does not exist in the schema
	 */
	public static RemoteEntitySchema getRemoteEntitySchema(RemoteEntitySchema entitySchema, String prop) {

		RemoteEntitySchema foundSchema = getRemoteEntitySchemaRecursively(new HashSet<>(), entitySchema, prop);

		if (foundSchema != null) {
			return foundSchema;
		} else {
			log.debug("property '{}' not found for schema: {}", prop, entitySchema);
			return null;
		}

	}

	/**
	 * The entity types an association accepts.
	 *
	 * <p>They are carried by the title of the association's {@code assoc:targetTypes} attribute, as a
	 * comma-separated list: {@code bcpg:compoListProduct} for instance admits
	 * {@code bcpg:rawMaterial,bcpg:semiFinishedProduct,bcpg:localSemiFinishedProduct,bcpg:finishedProduct}.
	 * A multi-valued association declares them on the array node itself, beside its items, so the same
	 * lookup serves both shapes.</p>
	 *
	 * @param entitySchema the schema of the entity holding the association
	 * @param assocName the association to look up, for instance {@code bcpg:compoListProduct}
	 * @return the declared target types, never {@code null}, empty when the association is unknown or declares none
	 */
	public static List<String> getAssocTargetTypes(RemoteEntitySchema entitySchema, String assocName) {
		return getAssocTargetTypes(getRemoteEntitySchema(entitySchema, assocName));
	}

	/**
	 * Same, for a caller that already holds the association schema.
	 *
	 * <p>Mind that a multi-valued association declares its target types on the array node itself and not on
	 * its items, so this is the node to pass, not {@code getItems()}.</p>
	 *
	 * @param assocSchema the schema of the association, may be {@code null}
	 * @return the declared target types, never {@code null}, empty when the schema declares none
	 */
	public static List<String> getAssocTargetTypes(RemoteEntitySchema assocSchema) {

		if (assocSchema == null) {
			return List.of();
		}

		RemoteEntitySchema targetTypes = assocSchema.getAttributeSchema(ATTR_TARGET_TYPES);

		if ((targetTypes == null) || (targetTypes.getTitle() == null)) {
			log.debug("no '{}' declared for schema: {}", ATTR_TARGET_TYPES, assocSchema);
			return List.of();
		}

		List<String> types = new ArrayList<>();
		for (String declared : targetTypes.getTitle().split(",")) {
			String type = declared.trim();
			if (!type.isEmpty()) {
				types.add(type);
			}
		}
		return types;
	}

	private static RemoteEntitySchema getRemoteEntitySchemaRecursively(Set<RemoteEntitySchema> visitedSchemas, RemoteEntitySchema entitySchema,
			String prop) {

		if (visitedSchemas.contains(entitySchema) || entitySchema.getProperties() == null) {
			return null;
		}

		for (Entry<String, RemoteEntitySchema> entry : entitySchema.getProperties().entrySet()) {

			final RemoteEntitySchema subSchema = entry.getValue();

			if (entry.getKey().equals(prop)) {
				return subSchema;
			}

			visitedSchemas.add(entitySchema);
			RemoteEntitySchema foundSchema = getRemoteEntitySchemaRecursively(visitedSchemas, subSchema, prop);

			if (foundSchema != null) {
				return foundSchema;
			}
		}

		return null;
	}

}
