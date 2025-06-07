package ru.yandex.practicum.bliushtein.spr5.rest;

import com.fasterxml.jackson.databind.Module;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication/*(
		nameGenerator = FullyQualifiedAnnotationBeanNameGenerator.class
)
@ComponentScan(
		basePackages = {"org.openapitools", "ru.yandex.practicum.bliushtein.spr5.rest.api" , "ru.yandex.practicum.bliushtein.spr5.rest.configuration"},
		nameGenerator = FullyQualifiedAnnotationBeanNameGenerator.class
)*/
public class RestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestApiApplication.class, args);
	}

	@Bean(name = "ru.yandex.practicum.bliushtein.spr5.rest.OpenApiGeneratorApplication.jsonNullableModule")
	public Module jsonNullableModule() {
		return new JsonNullableModule();
	}

}