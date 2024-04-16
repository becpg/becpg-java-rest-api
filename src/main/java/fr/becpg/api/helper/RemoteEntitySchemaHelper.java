package fr.becpg.api.helper;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.becpg.api.model.RemoteEntitySchema;

/**
 * Helper class for RemoteEntitySchema
 * 
 *  @author frederic
 *
 */
public class RemoteEntitySchemaHelper {

	private static final Logger log = LoggerFactory.getLogger(RemoteEntitySchemaHelper.class);

	private RemoteEntitySchemaHelper() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Helper method aimed to find recursively a given property in a {@link RemoteEntitySchema}
	 * 
	 * @param entitySchema
	 * @param prop
	 * @return the {@link RemoteEntitySchema} of the property we need or null if the property does not exist in the schema
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
