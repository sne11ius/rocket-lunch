
import org.gradle.api.JavaVersion.*
import org.hidetake.gradle.swagger.generator.GenerateSwaggerCode
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.2.6.RELEASE"
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	id("com.github.johnrengelman.processes") version "0.5.0"
	id("org.springdoc.openapi-gradle-plugin") version "1.0.0"
	id("org.hidetake.swagger.generator") version "2.18.2"

	val kotlinVersion = "1.3.71"
	kotlin("jvm") version kotlinVersion
	kotlin("plugin.spring") version kotlinVersion
	kotlin("plugin.jpa") version kotlinVersion
}

group = "rocket-lunch"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = VERSION_11

val developmentOnly: Configuration by configurations.creating
configurations {
	runtimeClasspath {
		extendsFrom(developmentOnly)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	val openapiVersion = "1.3.1"
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.flywaydb:flyway-core")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("io.github.microutils:kotlin-logging:1.7.9")
	implementation("org.springdoc:springdoc-openapi-webmvc-core:$openapiVersion")
	implementation("org.springdoc:springdoc-openapi-ui:$openapiVersion")
	"swaggerCodegen"("io.swagger.codegen.v3:swagger-codegen-cli:3.0.19")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("com.h2database:h2")
	runtimeOnly("org.postgresql:postgresql")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
}

dependencyManagement {
	imports {
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = VERSION_11.toString()
	}
}

openApi {
	apiDocsUrl.set("http://localhost:8090/v3/api-docs")
	outputDir.set(file("$buildDir"))
	waitTimeInSeconds.set(20)
}

swaggerSources {
	create("rocketlunch").apply {
		setInputFile(file("$buildDir/openapi.json"))
		code(closureOf<GenerateSwaggerCode> {
			language = "javascript"
			additionalProperties = mapOf(
					"usePromises" to "true",
					"useES6" to "true"
			)
		})
	}
}

tasks.withType<GenerateSwaggerCode> {
	// Make sure openapi.json is generated before use
	dependsOn("generateOpenApiDocs")
}

tasks {
	val apiDir = file("$buildDir/swagger-code-rocketlunch/src")
	val apiTargetDir = file("$rootDir/../frontend/api/generated")
	val syncApi by registering(Sync::class) {
		from(apiDir)
		into(apiTargetDir)
		dependsOn("generateSwaggerCode")
	}

	register("all") {
		dependsOn("assemble")
		dependsOn(syncApi)
	}
}
