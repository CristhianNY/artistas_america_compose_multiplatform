plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    application
    kotlin("plugin.serialization") version "2.0.0"
}

group = "org.artistasamerica.artistas"
version = "1.0.0"
application {
    mainClass.set("org.artistasamerica.artistas.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["io.ktor.development"] ?: "false"}")
}

tasks.create("stage") {
    dependsOn("installDist")
}

dependencies {
    implementation(projects.shared)
    implementation(libs.logback)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    testImplementation(libs.ktor.server.tests)
    testImplementation(libs.kotlin.test.junit)
    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.auth.jwt)
    implementation(libs.ktor.server.content.negotiation.jvm)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.json)
    implementation(libs.ktor.server.html.builder)
    implementation(libs.ktorm.core)
    implementation(libs.jbcrypt)
    implementation(libs.mysql.connector.java)
    implementation(libs.ktor.server.cio)
    implementation("io.ktor:ktor-server-cors:2.3.12")
}