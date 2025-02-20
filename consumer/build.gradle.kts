import org.springframework.boot.gradle.tasks.bundling.BootJar

configurations {
    create("agent")
}

dependencies {
    "agent"("io.opentelemetry.javaagent:opentelemetry-javaagent:2.13.0")

    implementation(project(":common"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("io.opentelemetry:opentelemetry-api:1.47.0")
}

tasks.register<Copy>("copyAgent") {
    from(configurations["agent"]) {
        rename("opentelemetry-javaagent-.*\\.jar", "opentelemetry-javaagent.jar")
    }
    into(layout.buildDirectory.dir("agent"))
}

tasks.withType<BootJar> {
    dependsOn("copyAgent")
    archiveFileName = "pct-consumer.jar"
}
