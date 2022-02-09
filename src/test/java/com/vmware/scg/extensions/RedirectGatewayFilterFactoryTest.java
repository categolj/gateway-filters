package com.vmware.scg.extensions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.http.HttpStatus.MOVED_PERMANENTLY;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RedirectGatewayFilterFactoryTest {
	@Autowired
	WebTestClient webTestClient;

	@Test
	void redirectForGet() {
		webTestClient
				.get()
				.uri("https://v1.example.com/hello")
				.header("Host", "v1.example.com")
				.exchange()
				.expectHeader().location("https://example.com/hello")
				.expectStatus().isEqualTo(MOVED_PERMANENTLY);
	}

	@Test
	void redirectForPot() {
		webTestClient
				.post()
				.uri("https://v1.example.com/hello")
				.header("Host", "v1.example.com")
				.exchange()
				.expectHeader().location("https://example.com/hello")
				.expectStatus().isEqualTo(MOVED_PERMANENTLY);
	}

	@SpringBootApplication
	public static class GatewayApplication {
		public static void main(String[] args) {
			SpringApplication.run(GatewayApplication.class, args);
		}
	}
}