import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

plugins {
	java
	id("io.spring.dependency-management")
	id("org.openapi.generator")
}

dependencyManagement {
	imports {
		mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
	}
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
sourceSets.main {
	java.srcDirs("$projectDir/build/generated/src/main/java")
}
dependencies {
	implementation(project(":service:service-api"))
	implementation(project(":data"))
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("org.springframework.boot:spring-boot-autoconfigure")
	implementation("org.springframework.data:spring-data-r2dbc")
	implementation("org.apache.commons:commons-lang3")
	implementation("com.fasterxml.jackson.core:jackson-databind")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.openapitools:jackson-databind-nullable:0.2.6")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.named<GenerateTask>("openApiGenerate") {
	generatorName.set("java")
	inputSpec.set("$rootDir/rest-api/src/main/resources/openapi.yaml")
	outputDir.set("${layout.buildDirectory.get()}/generated")
	ignoreFileOverride.set("$projectDir/.openapi-generator-ignore")
	modelPackage.set("ru.yandex.practicum.bliushtein.spr5.service.client.model")
	apiPackage.set("ru.yandex.practicum.bliushtein.spr5.service.client.api")
	configOptions.set(
		mapOf(
			"dateLibrary" to "java8",
			"reactive" to "true",
			"interfaceOnly" to "false",
			"library" to "webclient",
			"useTags" to "false",
			"useJakartaEe" to "true"
		)
	)
}
tasks.withType<Test> {
	useJUnitPlatform()
	testLogging {
		events("passed")
	}
}
tasks.named("compileJava") {
	dependsOn(tasks.named("openApiGenerate"))
}
