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
	public int add(@RequestParam(value = "a", defaultValue = "0") Integer a,
				   @RequestParam(value = "b", defaultValue = "0") Integer b){
		return a+b;
	}

	@GetMapping("/test")
	public int test(){
		Integer a = 6;
		Integer b = 6;

		return a+b;
	}
}
