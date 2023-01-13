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
    implementation(enforcedPlatform("io.quarkus.platform:quarkus-bom:2.15.3.Final"))
    implementation("io.quarkus:quarkus-google-cloud-functions")

    testImplementation("io.quarkus:quarkus-junit5:2.15.3.Final")
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