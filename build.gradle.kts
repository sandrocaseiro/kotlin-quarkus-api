group = "dev.sandrocaseiro"
version = "1.0.0-SNAPSHOT"

plugins {
    kotlin("jvm") version "1.4.31"
    kotlin("plugin.allopen") version "1.4.31"
    id("io.quarkus")
}

repositories {
    mavenLocal()
    mavenCentral()
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

val assertjVersion: String by project
val junitVintageVersion: String by project
val cucumberVersion: String by project
val restAssuredVersion: String by project
val wiremockVersion: String by project
val groovyVersion: String by project

project.ext {
    set("env", "dev")
}

dependencies {
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))

    implementation(kotlin("stdlib-jdk8"))
    implementation("io.quarkus:quarkus-kotlin")
    implementation("io.quarkus:quarkus-config-yaml")
    implementation("io.quarkus:quarkus-resteasy")
    implementation("io.quarkus:quarkus-arc")
    implementation("io.quarkus:quarkus-hibernate-validator")

    implementation("io.quarkus:quarkus-resteasy-jackson")
    implementation("io.quarkus:quarkus-jackson")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("io.quarkus:quarkus-hibernate-orm-panache-kotlin")
    implementation("io.quarkus:quarkus-jdbc-postgresql")

    implementation("io.quarkus:quarkus-resteasy-qute")
    implementation("io.quarkus:quarkus-smallrye-openapi")

    implementation("io.quarkus:quarkus-smallrye-jwt")
    implementation("io.quarkus:quarkus-smallrye-jwt-build")

    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("com.h2database", "h2")
    testImplementation("org.assertj", "assertj-core", assertjVersion)
    testImplementation("org.junit.vintage", "junit-vintage-engine", junitVintageVersion)
    testImplementation("io.cucumber", "cucumber-java", cucumberVersion)
    testImplementation("io.cucumber", "cucumber-junit", cucumberVersion)
    testImplementation("io.rest-assured:rest-assured")
//    testImplementation("com.github.tomakehurst", "wiremock-jre8", wiremockVersion)
    //testImplementation("org.codehaus.groovy", "groovy-json", "2.4.20")
}

allOpen {
    annotation("javax.ws.rs.Path")
    annotation("javax.enterprise.context.ApplicationScoped")
    annotation("io.quarkus.test.junit.QuarkusTest")
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

sourceSets {
    val main by getting
    val test by getting
    val integrationTest by creating {
        withConvention(org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet::class) {
            kotlin.srcDir("src/integration-test/kotlin")
        }
        resources.srcDir(file("src/integration-test/resources"))
        compileClasspath += main.compileClasspath + test.compileClasspath
        runtimeClasspath += main.runtimeClasspath + test.runtimeClasspath
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()
    kotlinOptions.javaParameters = true
}

listOf("local", "test", "prod").forEach { env ->
    tasks.register("quarkus${env.capitalize()}") {
        System.setProperty("quarkus.profile", env)
        ext["env"] = env

        finalizedBy(tasks.quarkusDev)
    }
}

tasks.register<Test>("integration-test") {
    testClassesDirs = sourceSets["integrationTest"].output.classesDirs
    classpath = sourceSets["integrationTest"].runtimeClasspath
}
