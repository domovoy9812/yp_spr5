package ru.yandex.practicum.bliushtein.spr5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BliushteinSprint5Application {

	public static void main(String[] args) {
		SpringApplication.run(BliushteinSprint5Application.class, args);
	}

}
