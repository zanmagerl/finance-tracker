val quarkusVersion = "2.15.3.Final"
val googleLibrariesBomVersion = "26.3.0"
val lombokVersion = "1.18.24"

plugins {
    java
    id("io.quarkus") version "2.15.3.Final"
    id("com.diffplug.spotless") version "6.12.1"
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    // Quarkus dependencies
    implementation(enforcedPlatform("io.quarkus.platform:quarkus-bom:$quarkusVersion"))
    implementation("io.quarkus:quarkus-google-cloud-functions")
    implementation("io.quarkiverse.loggingjson:quarkus-logging-json:1.1.1")

    // Google dependencies
    implementation(platform("com.google.cloud:libraries-bom:$googleLibrariesBomVersion"))
    implementation("com.google.cloud:google-cloud-bigquery")

    // Lombok
    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    testCompileOnly("org.projectlombok:lombok:$lombokVersion")
    testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")

    testImplementation("io.quarkus:quarkus-junit5:$quarkusVersion")
    testImplementation("io.rest-assured:rest-assured:4.5.1")
}

group = "si.magerl"
version = "1.0.0"
description = "Simple function for spending tracking"
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