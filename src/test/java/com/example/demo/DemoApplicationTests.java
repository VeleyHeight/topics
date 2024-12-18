package com.example.demo;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.hamcrest.text.MatchesPattern;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.restdocs.RestDocsRestAssuredConfigurationCustomizer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {
	@Container
	@ServiceConnection
	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13.2");
	@LocalServerPort
	private Integer port;
	@BeforeEach
	void setUp(){
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}
//	static {
//		postgres.start();
//	}
	@Test
	void createdTopics() {
		String string = """
				        {
				            "title": "Dynamic Mobility Producer",
				            "description": "Principal",
				            "parentId": 5
				        }
				""";
		RestAssured.given()
				.contentType("application/json")
				.body(string)
				.when()
				.post("/topics")
				.then()
				.statusCode(201)
				.body("id", Matchers.notNullValue())
				.body("title", Matchers.is("Dynamic Mobility Producer"))
				.body("description", Matchers.is("Principal"));
	}
}
