package com.sinensia.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.web.client.RestClientException;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment= WebEnvironment.RANDOM_PORT)
//@AutoConfigureWebClient
class DemoProjectApplicationTests {

	private static final String VARIABLE_B = "&b=";
	@Autowired transient TestRestTemplate restTemplate;
	@Autowired transient DemoProjectApplication app;

	@Test
	void contextLoads() {
	}

	@DisplayName("Test Root")
	@Test
	void rootTest() {
		assertThat(restTemplate.getForObject("/", String.class)).isEqualTo("Esta es la página inicial de Xavi Garcia!");
	}

	@Nested
	@DisplayName("Application Tests Hello")
	class AppHelloTest {

		@DisplayName("Test Hello")
		@Test
		void helloTestDefault() {
			assertThat(restTemplate.getForObject("/hello", String.class)).isEqualTo("Hello World!");
		}

		@DisplayName("Test Hello con Texto")
		@Test
		void helloTestVariable() {
			assertThat(restTemplate.getForObject("/hello?name=Xavi", String.class)).isEqualTo("Hello Xavi!");
		}

		@DisplayName("Test Hello con Variables")
		@Test
		void helloTestVariables() {
			String[] arr = {"Xavi", "Pedro"};
			for(String name: arr) {
				assertThat(restTemplate.getForObject("/hello?name=" + name, String.class))
						.isEqualTo("Hello " + name + "!");
			}
		}

		@DisplayName("Test Hello con Parametrizacion")
		@ParameterizedTest
		@ValueSource(strings = {"Pepe", "Paco", "Fran"})
		void helloParamNames(String name) {
			assertThat(restTemplate.getForObject("/hello?name=" + name, String.class))
					.isEqualTo("Hello " + name + "!");
		}

		@DisplayName("Test Hello Multiple, con CSV Interno")
		@ParameterizedTest(name = "{displayName} [{index}] ({arguments}) \"{0}\" -> \"{1}\" ")
		@CsvSource({
				"a, Hello a!",
				"b, Hello b!",
				"'', Hello World!",
				"' ', Hello  !",
				"first+last, Hello first last!"
		})
		void helloParamNamesCsv(String name, String expected) {
			assertThat(restTemplate.getForObject("/hello?name=" + name, String.class)).isEqualTo(expected);
		}
	}

	@Nested
	@DisplayName("Application Tests Add")
	class AppAddTest {

		@DisplayName("Test Add")
		@Test
		void canAdd() {
			assertThat(restTemplate.getForObject("/add?a=1&b=2", String.class)).isEqualTo("3");
		}

		@DisplayName("Test Add Decimales")
		@Test
		void canAddFraction() {
			assertThat(restTemplate.getForObject("/add?a=1.5&b=2", String.class)).isEqualTo("3.5");
		}

		@DisplayName("Test Add Negativo")
		@Test
		void canAddNegative() {
			assertThat(restTemplate.getForObject("/add?a=1&b=-2", String.class)).isEqualTo("-1");
		}

		@DisplayName("Test Add Multiple")
		@Test
		void canAddMultiple() {
			assertThat(restTemplate.getForObject("/add?a=1&b=2", String.class)).isEqualTo("3");
			assertThat(restTemplate.getForObject("/add?a=0&b=2", String.class)).isEqualTo("2");
		}

		@DisplayName("Test Add Multiple, con CSV Interno")
		@ParameterizedTest(name = "[{index}] ({arguments}) {0} + {1} = {2}")
		@CsvSource({
				"1, 2, 3",
				"0, 2, 2",
				"10, 2, 12",
				"-1, 3, 2",
				"1.5, 1, 2.5",
				"1.25, 1.25, 2.5"
		})
		void canAddMultipleCsv(String a, String b, String expected) {
			assertThat(restTemplate.getForObject("/add?a=" + a + VARIABLE_B + b, String.class)).isEqualTo(expected);
		}

		@DisplayName("Test Add, con Error por Tipo Incorrecto")
		@Test
		void canAddExceptionJsonString() {
			assertThat(restTemplate.getForObject("/add?a=z&b=2", String.class).indexOf("Bad Request"))
					.isGreaterThan(-1);
		}

		@DisplayName("Test Add, con formato Float")
		@Test
		void canAddFloat() {
			assertThat(restTemplate.getForObject("/add?a=1.5&b=2", Float.class)).isEqualTo(3.5f);
		}

		@DisplayName("Test Add, con Error por Tipo Incorrecto Float")
		@Test
		void canAddFloatException() {
			assertThrows(RestClientException.class, ()-> restTemplate.getForObject("/add?a=hola&b=2", Float.class));
		}

		@DisplayName("Test Add Multiple, con CSV Interno validando Float")
		@ParameterizedTest(name = "[{index}] ({arguments}) {0} + {1} = {2}")
		@CsvSource({
				"1, 2, 3",
				"0, 2, 2",
				"10, 2, 12",
				"-1, 3, 2",
				"1.5, 1, 2.5",
				"1.25, 1.25, 2.5"
		})
		void canAddParameterizedFloatCsv(String a, String b, String expected) {
			assertThat(restTemplate.getForObject("/add?a=" + a + VARIABLE_B + b, Float.class))
					.isEqualTo(Float.parseFloat(expected));
		}

		/* Loss-of-precision by converting by converting Float return value into Integer
		@Test
		void canAddInteger(){
			assertThat(restTemplate.getForObject("/add?a=1.5&b=2", Integer.class)).isEqualTo(3.5f);
		}
		*/
	}

	@Nested
	@DisplayName("Application Tests")
	class AppTest {

		@DisplayName("App Test Retorno Integer")
		@Test
		void appCanAddReturnsInteger() {
			assertThat(app.add(1f, 2f)).isEqualTo(3);
		}

		@DisplayName("App Test Retorno Float")
		@Test
		void appCanAddReturnsFloat() {
			assertThat(app.add(1.5f, 2f)).isEqualTo(3.5f);
		}

		@DisplayName("App Test Retorno Null")
		@Test
		void appCanAddNull() {
			Exception thrown = assertThrows(NullPointerException.class, ()-> app.add(null, 2f));

			assertTrue(thrown.toString().contains("NullPointerException"));
			// alternatively check thrown.getMessage().contains("");
		}
	}

	@Nested
	@DisplayName("Application Tests Multiply")
	class MultiplicationTests {
		@DisplayName("Test Multiply Multiple, con CSV Interno")
		@ParameterizedTest(name = "[{index}] ({arguments}) {0} * {1} = {2}")
		@CsvSource({
				"1, 2, 2",
				"1, 1, 1",
				"1.0, 1.0, 1",
				"1, -2, -2",
				"'', 2, 0",
				"1.5, 1.5, 2.25"
		})
		void canMultiplyCsv(String a, String b, String expected) {
			assertThat(restTemplate.getForObject("/multiply?a=" + a + VARIABLE_B + b, String.class))
					.isEqualTo(expected);
		}
	}

	@Nested
	@DisplayName("Application Tests Subtraction")
	class SubtractionTests {
		@DisplayName("Test Subtraction Multiple, con CSV Interno")
		@ParameterizedTest(name = "[{index}] ({arguments}) {0} * {1} = {2}")
		@CsvSource({
				"1, 1, 0",
				"1, 2, -1",
				"5.0, 2.0, 3",
				"1, -2, 3",
				"3.5, 1.25, 2.25"
		})
		void canSubstractCsv(String a, String b, String expected) {
			assertThat(restTemplate.getForObject("/subtraction?a=" + a + VARIABLE_B + b, String.class))
					.isEqualTo(expected);
		}
	}

	@Nested
	@DisplayName("Application Tests Division")
	class DivisionTests {

		@Test
		void canDivideByZero() {
			assertThrows(RestClientException.class, () -> restTemplate.getForObject("/division?a=10&b=0", BigDecimal.class));
		}

		@DisplayName("Test Division Multiple, con CSV Interno")
		@ParameterizedTest(name = "[{index}] ({arguments}) {0} * {1} = {2}")
		@CsvSource({
				"4, 2, 2.00",
				"6, 2, 3.00",
				"10, 2, 5.00",
				"10, -1, -10.00",
				" 1.0, 1.0, 1.00",
				"10, 3, 3.33"
		})
		void canDivideCsv(String a, String b, String expected) {
			assertThat(restTemplate.getForObject("/division?a=" + a + VARIABLE_B + b, String.class))
					.isEqualTo(expected);
		}
	}

	@Nested
	@DisplayName("Application Tests Raiz Cuadrada")
	class RaizCuadradaTests {

		@DisplayName("Test Raiz Cuadrada Multiple, con CSV Interno")
		@ParameterizedTest(name = "[{index}] ({arguments}) Raiz Cuadrada de {0} = {1}")
		@CsvSource({
				"16, 4.0",
				"25, 5.0",
				"12, 3.5",
				"0, 0.0"
		})
		void canSqrtCsv(String a, String expected) {
			assertThat(restTemplate.getForObject("/sqrt?a=" + a, String.class))
					.isEqualTo(expected);
		}

		@Test
		void canSqrtNegative() {
			assertThrows(RestClientException.class, () -> restTemplate.getForObject("/sqrt?a=-1", Float.class));
		}
	}

	@DisplayName("Test Propio de Xavi")
	@Test
	void canTest() {
		assertThat(restTemplate.getForObject("/test", String.class)).isEqualTo("12");
	}
}