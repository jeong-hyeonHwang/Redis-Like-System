plugins {
    java
    id("org.springframework.boot") version "3.4.3"
    id("io.spring.dependency-management") version "1.1.7"
}

allprojects {
    group = "com.jhh"
    version = "0.0.1-SNAPSHOT"
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(17)
        }
    }


    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-security")
        implementation("org.springframework.boot:spring-boot-starter-validation")
        implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.3")
        implementation("org.springframework.batch:spring-batch-core:5.1.0")

        implementation("org.springframework.boot:spring-boot-starter")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        compileOnly("org.projectlombok:lombok")
        testCompileOnly("org.projectlombok:lombok")
        annotationProcessor("org.projectlombok:lombok")
        testAnnotationProcessor("org.projectlombok:lombok")

        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")

        runtimeOnly("com.mysql:mysql-connector-j")

        val querydslVersion = "5.1.0"
        val blazePersistenceVersion = "1.6.14"

        implementation("com.querydsl:querydsl-jpa:${querydslVersion}:jakarta")
        annotationProcessor("com.querydsl:querydsl-apt:${querydslVersion}:jakarta")
        annotationProcessor("jakarta.persistence:jakarta.persistence-api")

        implementation("com.blazebit:blaze-persistence-core-api-jakarta:${blazePersistenceVersion}")
        runtimeOnly("com.blazebit:blaze-persistence-core-impl-jakarta:${blazePersistenceVersion}")
        implementation("com.blazebit:blaze-persistence-integration-hibernate-6.2:${blazePersistenceVersion}")
        implementation("com.blazebit:blaze-persistence-integration-querydsl-expressions-jakarta:${blazePersistenceVersion}")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

project(":module-core") {
    dependencies {
    }
}

project(":module-api") {
    dependencies {
        implementation(project(":module-core"))
    }
}
