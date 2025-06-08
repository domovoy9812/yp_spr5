import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

plugins {
	java
	id("org.springframework.boot")
	id("io.spring.dependency-management")
	id("org.openapi.generator")
}

group = "ru.yandex.practicum.bliushtein"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}
sourceSets.main {
	java.srcDirs("$projectDir/build/generated/src/main/java")
}
sourceSets.test {
	java.srcDirs("$projectDir/build/generated/src/test/java")
}
//TODO использовать динамически сгенерированные классы при сборке; удалить закоммиченые классы
tasks.named<GenerateTask>("openApiGenerate") {
	generatorName.set("spring")
	inputSpec.set("${layout.projectDirectory}/src/main/resources/openapi.yaml")
	outputDir.set("${layout.buildDirectory.get()}/generated")
	ignoreFileOverride.set("$projectDir/.openapi-generator-ignore")
	modelPackage.set("ru.yandex.practicum.bliushtein.spr5.rest.model")
	apiPackage.set("ru.yandex.practicum.bliushtein.spr5.rest.api")
	invokerPackage.set("ru.yandex.practicum.bliushtein.spr5.rest")
	configOptions.set(
		mapOf(
			"dateLibrary" to "java8",
			"reactive" to "true",
			"interfaceOnly" to "false",
			"library" to "spring-boot",
			"useTags" to "false",
			"useSpringBoot3" to "true",
			"openApiNullable" to "true"
		)
	)
	doLast {
		delete("${layout.buildDirectory.get()}/generated/src/main/java/ru/yandex/practicum/bliushtein/spr5/rest/api/BalanceApiController.java")
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.8.8")
	implementation("org.openapitools:jackson-databind-nullable:0.2.6")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.named("compileJava") {
	dependsOn(tasks.named("openApiGenerate"))
}
