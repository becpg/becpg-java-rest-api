package fr.becpg.api.helper;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

/**
 * Utility class to manage {@link Resource}
 */
public class ResourcesHelper {

	private ResourcesHelper() {
		super();
	}

	/**
	 * Converts a {@link Resource} to a String value
	 * @param resource
	 * @return
	 * @throws IOException 
	 */
	public static String asString(Resource resource) throws IOException {
		Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
		return FileCopyUtils.copyToString(reader);
	}
}
