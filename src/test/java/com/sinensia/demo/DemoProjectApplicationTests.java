package com.sinensia.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.web.client.RestClientException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebClient
class DemoProjectApplicationTests {

	@Autowired TestRestTemplate restTemplate;

	@Test
	void contextLoads() {
	}

	@Test
	void rootTest(){
		assertThat(restTemplate.getForObject("/", String.class)).isEqualTo("Esta es la página inicial de Xavi Garcia!");
	}

	@Test
	void helloTestDefault(){
		assertThat(restTemplate.getForObject("/hello", String.class)).isEqualTo("Hello World!");
	}

	@Test
	void helloTestVariable(){
		assertThat(restTemplate.getForObject("/hello?name=Xavi", String.class)).isEqualTo("Hello Xavi!");
	}

	@Test
	void helloTestVariables(){
		String[] arr = {"Xavi", "Pedro"};
		for(String name: arr) {
			assertThat(restTemplate.getForObject("/hello?name=" + name, String.class))
					.isEqualTo("Hello " + name + "!");
		}
	}

	@ParameterizedTest
	@ValueSource(strings = {"Pepe", "Paco", "Fran"})
	void helloParamNames(String name){
		assertThat(restTemplate.getForObject("/hello?name=" + name, String.class))
				.isEqualTo("Hello " + name + "!");
	}

	@DisplayName("Test Multiple, con CSV interno")
	@ParameterizedTest(name = "[{index}] ({arguments}) \"{0}\" -> \"{1}\" ")
	@CsvSource({
			"a, Hello a!",
			"b, Hello b!",
			"'', Hello World!",
			"' ', Hello  !",
			"first+last, Hello first last!"
	})
	void helloParamNamesCsv(String name, String expected){
		assertThat(restTemplate.getForObject("/hello?name=" + name, String.class)).isEqualTo(expected);
	}

	@Test
	void canAdd(){
		assertThat(restTemplate.getForObject("/add?a=1&b=2", String.class)).isEqualTo("3");
	}

	@Test
	void canAddFraction(){
		assertThat(restTemplate.getForObject("/add?a=1.5&b=2", String.class)).isEqualTo("3.5");
	}

	@Test
	void canAddNegative(){
		assertThat(restTemplate.getForObject("/add?a=1&b=-2", String.class)).isEqualTo("-1");
	}

	@Test
	void canAddMultiple(){
		assertThat(restTemplate.getForObject("/add?a=1&b=2", String.class)).isEqualTo("3");
		assertThat(restTemplate.getForObject("/add?a=0&b=2", String.class)).isEqualTo("2");
	}

	@ParameterizedTest(name = "[{index}] ({arguments}) {0} + {1} = {2}")
	@CsvSource({
			"1, 2, 3",
			"0, 2, 2",
			"10, 2, 12",
			"-1, 3, 2",
			"1.5, 1, 2.5",
			"1.25, 1.25, 2.5"
	})
	void canAddMultipleCsv(String a, String b, String expected){
		assertThat(restTemplate.getForObject("/add?a=" + a + "&b=" + b, String.class)).isEqualTo(expected);
	}

	@Test
	void canAddExceptionJsonString(){
		assertThat(restTemplate.getForObject("/add?a=z&b=2", String.class).indexOf("Bad Request"))
				.isGreaterThan(-1);
	}

	@Test
	void canAddFloat(){
		assertThat(restTemplate.getForObject("/add?a=1.5&b=2", Float.class)).isEqualTo(3.5f);
	}

	@Test
	void canAddFloatException(){
		Exception thrown = assertThrows(RestClientException.class, ()->{
			restTemplate.getForObject("/add?a=hola&b=2", Float.class);
		});
	}

	@Test
	void canTest(){
		assertThat(restTemplate.getForObject("/test", String.class)).isEqualTo("12");
	}
}
