plugins {
    kotlin("jvm")
}

group = "org.isc4x51.dan.creadorManual"
version = "2.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation("org.apache.commons:commons-io:1.3.2")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}