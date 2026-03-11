import org.openapitools.generator.gradle.plugin.tasks.GenerateTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.openapi.generator") version "7.3.0"
    id("org.jetbrains.kotlin.jvm")
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.5.9"
    id("io.spring.dependency-management") version "1.1.7"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

kotlin {
    jvmToolchain(21)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.15")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

sourceSets {
    main {
        kotlin.srcDir("$buildDir/generated/server/src/main/kotlin")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

openApiGenerate {
    generatorName.set("kotlin-spring")
    inputSpec.set("$projectDir/openapi.yaml")
    outputDir.set("$buildDir/generated/server")

    apiPackage.set("ru.tbank.education.school.lesson7.seminar.api")
    modelPackage.set("ru.tbank.education.school.lesson7.seminar.model")

    configOptions.set(
        mapOf(
            "interfaceOnly" to "true",
            "useTags" to "true",
            "enumPropertyNaming" to "UPPERCASE",
            "useSpringBoot3" to "true",
            "serializationLibrary" to "jackson"
        )
    )
}

tasks.withType<KotlinCompile> {
    dependsOn(tasks.named("openApiGenerate"))
}

tasks.register<GenerateTask>("generateClient") {
    generatorName.set("kotlin-spring")
    inputSpec.set("$projectDir/openapi.yaml")
    outputDir.set("$buildDir/generated/client")

    apiPackage.set("ru.tbank.education.school.lesson7.seminar.client.api")
    modelPackage.set("ru.tbank.education.school.lesson7.seminar.client.model")

    configOptions.set(
        mapOf(
            "library" to "spring-cloud",
            "useSpringBoot3" to "true",
            "serializationLibrary" to "jackson"
        )
    )
}
