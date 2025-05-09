plugins {
	java
	id("org.springframework.boot")
	id("io.spring.dependency-management")
}

group = "ru.yandex.practicum.bliushtein"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	runtimeOnly(project(":web"))
	runtimeOnly(project(":service:service-impl"))

	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-actuator")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.boot:spring-boot-testcontainers")
	testImplementation("org.testcontainers:junit-jupiter")
	testImplementation("org.testcontainers:postgresql")
	testImplementation("org.springframework.boot:spring-boot-starter-web")
	testImplementation(project(":data"))
	testImplementation("org.springframework.data:spring-data-jpa")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	//developmentOnly("org.springframework.boot:spring-boot-docker-compose")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
