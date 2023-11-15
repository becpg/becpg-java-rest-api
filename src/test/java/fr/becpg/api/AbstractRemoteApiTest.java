package fr.becpg.api;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

@SpringBootTest
public abstract class AbstractRemoteApiTest {
	
	
	@Value("classpath:entities.json")
	protected Resource entities;

	@Value("classpath:entity.json")
	protected Resource entity;
	

	@Value("classpath:channel.json")
	protected Resource channel;



	protected String asString(Resource resource) throws IOException {
		Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
		return FileCopyUtils.copyToString(reader);
	}
	
}
