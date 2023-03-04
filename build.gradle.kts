buildscript {
    dependencies {
        classpath("com.google.cloud.tools:jib-quarkus-extension-gradle:0.1.2")
    }
}

val quarkusVersion = "2.15.3.Final"
val googleLibrariesBomVersion = "26.3.0"
val quarkusGoogleCloudServicesVersion = "1.4.0"
val lombokVersion = "1.18.24"
val mockitoVersion = "4.4.0"

plugins {
    java
    id("io.quarkus") version "2.15.3.Final"
    id("com.diffplug.spotless") version "6.12.1"
    id("com.google.cloud.tools.jib") version "3.2.1"
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    // Quarkus dependencies
    implementation(enforcedPlatform("io.quarkus.platform:quarkus-bom:$quarkusVersion"))
    implementation("io.quarkus:quarkus-resteasy-reactive")
    implementation("io.quarkus:quarkus-resteasy-reactive-jackson")
    implementation("io.quarkus:quarkus-security")
    implementation("io.quarkiverse.googlecloudservices:quarkus-google-cloud-firestore:$quarkusGoogleCloudServicesVersion")
    implementation("io.quarkiverse.googlecloudservices:quarkus-google-cloud-bigquery:$quarkusGoogleCloudServicesVersion")
    implementation("io.quarkiverse.googlecloudservices:quarkus-google-cloud-logging:$quarkusGoogleCloudServicesVersion")
    implementation("io.quarkiverse.googlecloudservices:quarkus-google-cloud-firebase-admin:$quarkusGoogleCloudServicesVersion")
    implementation("org.mapstruct:mapstruct:1.5.3.Final")

    // Google dependencies
    implementation(platform("com.google.cloud:libraries-bom:$googleLibrariesBomVersion"))
    implementation("com.google.cloud:google-cloud-bigquery")

    // Lombok
    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.3.Final")

    testImplementation("io.quarkus:quarkus-junit5:$quarkusVersion")
    testImplementation("org.mockito:mockito-core:$mockitoVersion")
    testImplementation("io.rest-assured:rest-assured:4.5.1")
    testCompileOnly("org.projectlombok:lombok:$lombokVersion")
    testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")
}

jib {
    to {
        image = "europe-west3-docker.pkg.dev/home-automation-378219/finance-tracker-repository/finance-tracker"
    }
    from {
        image = "eclipse-temurin:17"
    }
    container {
        mainClass = "bogus"  // to suppress Jib warning about missing main class
        jvmFlags = listOf("-Dquarkus.http.host=0.0.0.0")
        ports = listOf("8080")
    }
    pluginExtensions {
        pluginExtension {
            implementation = "com.google.cloud.tools.jib.gradle.extension.quarkus.JibQuarkusExtension"
            properties = mapOf("packageType" to "fast-jar")
        }
    }
}

group = "si.magerl"
version = "1.0.0"
description = "Simple microservice for finance tracking"
java.sourceCompatibility = JavaVersion.VERSION_17

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

spotless {
    java {
        importOrder()
        formatAnnotations()
        removeUnusedImports()
        palantirJavaFormat()
    }
}

tasks.named<Task>("build") {
    dependsOn("spotlessApply")
}