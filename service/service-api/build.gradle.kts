plugins {
	java
	id("io.spring.dependency-management")
}

group = "ru.yandex.practicum.bliushtein"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}
dependencyManagement {
	imports {
		mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
	}
}
repositories {
	mavenCentral()
}
dependencies {
	implementation("io.projectreactor:reactor-core")
}

