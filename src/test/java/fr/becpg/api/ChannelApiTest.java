package fr.becpg.api;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import fr.becpg.api.handler.ChannelAPI;
import fr.becpg.api.model.ChannelAPIModel;
import fr.becpg.api.model.RemoteEntity;
import fr.becpg.api.model.RemoteEntityRef;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

@SpringBootTest
class ChannelApiTest extends AbstractRemoteApiTest {

	Logger logger = LoggerFactory.getLogger(ChannelApiTest.class);

	@Autowired
	private ChannelAPI channelAPI;

	private static MockWebServer mockBackEnd;

	@BeforeAll
	static void setUp() throws IOException {
		mockBackEnd = new MockWebServer();
		mockBackEnd.start();

	}

	@AfterAll
	static void tearDown() throws IOException {
		mockBackEnd.shutdown();
	}

	@DynamicPropertySource
	static void properties(DynamicPropertyRegistry r) throws IOException {
		r.add("content.service.url", () -> "http://localhost:" + mockBackEnd.getPort());
	}

	@Test
	void testChannelApi() throws IOException {

		mockBackEnd.enqueue(new MockResponse().setBody(asString(channel)).addHeader("Content-Type", "application/json"));

		RemoteEntity channel = channelAPI.get("sample-channel");
		Assert.assertNotNull(channel.getStringProp(ChannelAPIModel.PROP_CHANNEL_CONFIG));

		mockBackEnd.enqueue(new MockResponse().setBody(asString(entities)).addHeader("Content-Type", "application/json"));

		List<RemoteEntityRef> entities = channelAPI.list("sample-channel");
		for (RemoteEntityRef entityRef : entities) {

			Assert.assertNotNull(entityRef.getEntity());

		}

	}

	@Test
	void testSessionCookiePreferredOverAuthorization() throws Exception {
		int previousRequestCount = mockBackEnd.getRequestCount();

		mockBackEnd.enqueue(new MockResponse().setBody(asString(channel)).addHeader("Content-Type", "application/json")
				.addHeader("Set-Cookie", "JSESSIONID=session-123; Path=/alfresco; HttpOnly"));

		mockBackEnd.enqueue(new MockResponse().setBody(asString(entities)).addHeader("Content-Type", "application/json"));

		channelAPI.get("sample-channel");
		channelAPI.list("sample-channel");

		for (int i = 0; i < previousRequestCount; i++) {
			mockBackEnd.takeRequest();
		}

		RecordedRequest firstRequest = mockBackEnd.takeRequest();
		RecordedRequest secondRequest = mockBackEnd.takeRequest();

		Assertions.assertNotNull(firstRequest.getHeader("Authorization"));
		Assertions.assertTrue(secondRequest.getHeader("Cookie").contains("JSESSIONID=session-123"));
		Assertions.assertNull(secondRequest.getHeader("Authorization"));
	}
}
