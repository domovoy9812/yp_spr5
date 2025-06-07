plugins {
	id("org.springframework.boot") version "3.4.5" apply false
	id("io.spring.dependency-management") version "1.1.7" apply false
	id("org.openapi.generator") version "7.12.0" apply false
}

group = "ru.yandex.practicum.bliushtein"
version = "0.0.1-SNAPSHOT"

repositories {
	mavenCentral()
}
