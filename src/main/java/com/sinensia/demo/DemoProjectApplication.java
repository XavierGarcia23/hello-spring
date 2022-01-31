package com.sinensia.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoProjectApplication {

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
		Float sum = a + b;
		Float decimals = sum - sum.intValue();
		if(decimals != 0){
			return sum;
		}
		return Integer.valueOf(sum.intValue());
	}

	@GetMapping("/multiply")
	public Object multiply(@RequestParam(value = "a", defaultValue = "0") Float a,
						   @RequestParam(value = "b", defaultValue = "0") Float b){
		Float product = a * b;
		Float decimals = product - product.intValue();
		if(decimals != 0){
			return product;
		}
		return Integer.valueOf(product.intValue());
	}

	@GetMapping("/subtraction")
	public Object subtraction(@RequestParam(value = "a", defaultValue = "0") Float a,
							  @RequestParam(value = "b", defaultValue = "0") Float b){
		Float subtraction = a - b;
		Float decimals = subtraction - subtraction.intValue();
		if(decimals != 0){
			return subtraction;
		}
		return Integer.valueOf(subtraction.intValue());
	}

	@GetMapping("/division")
	public Object division(@RequestParam(value = "a", defaultValue = "0") Float a,
						   @RequestParam(value = "b", defaultValue = "0") Float b){
		Float division = a / b;
		Float decimals = division - division.intValue();
		if(decimals != 0){
			return division;
		}
		return Integer.valueOf(division.intValue());
	}

	@GetMapping("/test")
	public int test(){
		Integer a = 6;
		Integer b = 6;

		return a+b;
	}
}
