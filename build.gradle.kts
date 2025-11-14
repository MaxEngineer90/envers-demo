plugins {
    id("org.springframework.boot") version "3.5.7"
    id("io.spring.dependency-management") version "1.1.6"
    id("io.freefair.lombok") version "9.1.0"
    java
}

group = "de.ml.tutorials.envers.demo"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Spring
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Liquibase
    implementation("org.liquibase:liquibase-core")

    // Envers
    implementation("org.hibernate.orm:hibernate-envers")

    // Oracle JDBC
    implementation("com.oracle.database.jdbc:ojdbc11:23.5.0.24.07")

    // MapStruct
    implementation("org.mapstruct:mapstruct:1.5.5.Final")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
}

tasks.withType<Test> {
    enabled = false
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-Amapstruct.defaultComponentModel=spring")
}