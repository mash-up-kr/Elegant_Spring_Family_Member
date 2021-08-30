import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
	val bootVer = "2.5.3"
	val kotlinVer = "1.5.21"
	val springMgmtVer = "1.0.11.RELEASE"

	id("org.springframework.boot") version bootVer
	id("io.spring.dependency-management") version springMgmtVer
	kotlin("jvm") version kotlinVer
	kotlin("plugin.spring") version kotlinVer
	kotlin("plugin.jpa") version kotlinVer
}


buildscript {
	repositories {
		mavenCentral()
	}
}

allprojects {
	repositories {
		mavenCentral()
	}

}

subprojects {
	val loggerVer = "2.0.4"

	apply(plugin = "org.springframework.boot")
	apply(plugin = "io.spring.dependency-management")
	apply(plugin = "java")
	apply(plugin = "kotlin")
	apply(plugin = "kotlin-kapt")
	apply(plugin = "kotlin-jpa")
	apply(plugin = "kotlin-spring")

	the<io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension>().apply {
		imports {
			mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
		}
	}

	group = "mashup.backend.spring"
	version = "0.0.1-SNAPSHOT"
	java.sourceCompatibility = JavaVersion.VERSION_11

	dependencies {
		implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
		implementation("org.jetbrains.kotlin:kotlin-reflect")
		implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
		implementation("io.github.microutils:kotlin-logging:$loggerVer")
		testImplementation("org.springframework.boot:spring-boot-starter-test") {
			exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
		}
	}

	repositories {
		mavenCentral()
	}

	tasks.withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
			jvmTarget = "11"
		}
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}
}

tasks.withType<BootJar> {
	enabled = false
}
