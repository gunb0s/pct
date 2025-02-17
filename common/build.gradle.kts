plugins {
    id("com.github.davidmc24.gradle.plugin.avro") version "1.9.1"
}

dependencies {
    implementation("io.confluent:kafka-avro-serializer:7.8.0")
    api("org.apache.avro:avro:1.12.0")
}
