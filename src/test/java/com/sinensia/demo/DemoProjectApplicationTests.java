package com.sinensia.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebClient
class DemoProjectApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void rootTest(@Autowired TestRestTemplate restTemplate){
		/*String ret = restTemplate.getForObject("/", String.class);
		boolean b = ret == "Esta es la página inicial de Xavi Garcia!";
		if(!b){
			throw new Exception("Algo")
		}*/

		assertThat(restTemplate.getForObject("/", String.class)).isEqualTo("Esta es la página inicial de Xavi Garcia!");
	}

	@Test
	void helloTestDefault(@Autowired TestRestTemplate restTemplate){
		assertThat(restTemplate.getForObject("/hello", String.class)).isEqualTo("Hello World!");
	}

	@Test
	void helloTestVariable(@Autowired TestRestTemplate restTemplate){
		assertThat(restTemplate.getForObject("/hello?name=Xavi", String.class)).isEqualTo("Hello Xavi!");
	}
}
