
import org.gradle.api.JavaVersion.*
import org.hidetake.gradle.swagger.generator.GenerateSwaggerCode
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.2.6.RELEASE"
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	id("com.github.johnrengelman.processes") version "0.5.0"
	id("org.springdoc.openapi-gradle-plugin") version "1.0.0"
	id("org.hidetake.swagger.generator") version "2.18.2"
	id("com.google.cloud.tools.jib") version "2.1.0"
	id("com.dorongold.task-tree") version "1.5"

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
	"swaggerCodegen"("org.openapitools:openapi-generator-cli:4.3.0")
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

jib {
	from {
		image = "gcr.io/distroless/java:11-debug"
	}
	to {
		image = "sne11ius/rocket-lunch-backend"
	}
}

tasks.build {
	dependsOn("jib")
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
			language = "typescript-fetch"
			additionalProperties = mapOf(
				// js ecosystem is ... always a pleasure.
				// ... find strange errors while npm run server
				// ... google 2 hours
				// find this friggn commit: https://github.com/OpenAPITools/openapi-generator/pull/3801/commits/0aa4262ef6b2926b74851922107b696bdb192e7e
				// ... see https://github.com/OpenAPITools/openapi-generator/blob/master/docs/generators/typescript-fetch.md
				"typescriptThreePlus" to "true"
			)
		})
	}
}

tasks.withType<GenerateSwaggerCode> {
	// Make sure openapi.json is generated before use
	dependsOn("generateOpenApiDocs")
}

tasks {
	val apiDir = file("$buildDir/swagger-code-rocketlunch")
	val apiTargetDir = file("$rootDir/../frontend/src/api/generated")
	val syncApi by registering(Sync::class) {
		from(apiDir)
		into(apiTargetDir)
		dependsOn("generateSwaggerCode")
	}
	build {
		dependsOn(syncApi)
	}
}
