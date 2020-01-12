package com.serverless;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.function.context.FunctionCatalog;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.function.Function;
import java.util.function.Consumer;
import java.util.function.Supplier;

@SpringBootApplication
public class SpringCloudFunctionExampleApplication {

	@Value("${name}")
	private String name;

	public static void main(String[] args) {
		String jarPath = "/Users/Jennifer/Projects/seperateFun/target/seperateFun-0.0.1-SNAPSHOT.jar";
		String arg1 = "--spring.cloud.function.location=" + jarPath;
		ApplicationContext context = SpringApplication.run(SpringCloudFunctionExampleApplication.class, arg1);
		FunctionCatalog catalog = context.getBean(FunctionCatalog.class);
		Function<String, String> func = catalog.lookup("upperCase");
		Consumer<String> consume = catalog.lookup("consume");
		consume.accept(func.apply("hello feng"));
		//func.apply("hello brynn");
	}

	@Bean
	public Consumer<String> consume() {
		return input -> System.out.print("input is: " + input);

	}

	@Bean
	public Supplier<String> supply() {
		return () -> "Hello Spring " + name;

	}




}
