package com.sinensia.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.processing.Generated;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@SpringBootApplication
@RestController
public class DemoProjectApplication {

	@Generated(value="org.springframework.boot")
	public static void main(String[] args) {
		SpringApplication.run(DemoProjectApplication.class, args);
	}

	@GetMapping("/")
	public String home(){
		return "Esta es la página inicial de Xavi Garcia!";
	}

	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name){
		return String.format("Hello %s!", name);
	}

	@GetMapping("/add")
	public Object add(@RequestParam(value = "a", defaultValue = "0") Float a,
					  @RequestParam(value = "b", defaultValue = "0") Float b){
		float sum = a + b;
		float decimals = sum - (int) sum;
		if(decimals != 0){
			return sum;
		}
		return (int) sum;
	}

	@GetMapping("/multiply")
	public Object multiply(@RequestParam(value = "a", defaultValue = "0") Float a,
						   @RequestParam(value = "b", defaultValue = "0") Float b){
		float product = a * b;
		float decimals = product - (int) product;
		if(decimals != 0){
			return product;
		}
		return (int) product;
	}

	@GetMapping("/subtraction")
	public Object subtraction(@RequestParam(value = "a", defaultValue = "0") Float a,
							  @RequestParam(value = "b", defaultValue = "0") Float b){
		float subtraction = a - b;
		float decimals = subtraction - (int) subtraction;
		if(decimals != 0){
			return subtraction;
		}
		return (int) subtraction;
	}

	//@GetMapping("/division")
	/*public Object division(@RequestParam(value = "a", defaultValue = "0") Float a,
						   @RequestParam(value = "b", defaultValue = "0") Float b
	)throws Exception {
		if (0f == b){
			throw new Exception("Division by Zero");
		}

		Float division = a / b;
		Float decimals = division - division.intValue();
		if(decimals != 0){
			return division;
		}
		return Integer.valueOf(division.intValue());
	}*/

	//Otro ejemplo con decimales
	@GetMapping("/division")
	public BigDecimal division(@RequestParam(value = "a", defaultValue = "0") BigDecimal a,
							   @RequestParam(value = "b", defaultValue = "0") BigDecimal b){
		return a.divide(b, 2, RoundingMode.HALF_DOWN);
	}

	@GetMapping("/sqrt")
	public Float sqrt(@RequestParam(value = "a", defaultValue = "0") Float a
	) throws Exception {
		if (a < 0){
			throw new Exception("Raiz Cuadrada de un numero negativo da error");
		}

		BigDecimal number = BigDecimal.valueOf(a);

		number = number.sqrt(new MathContext(2));

		return number.floatValue();

		/*Integer number = a;
		double result = Math.sqrt(number);

		return result;*/
	}

	@GetMapping("/test")
	public int test(){
		Integer a = 6;
		Integer b = 6;

		return a+b;
	}
}