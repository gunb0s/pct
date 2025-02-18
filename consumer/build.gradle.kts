import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    implementation(project(":common"))
}

tasks.withType<BootJar> {
    archiveFileName = "pct-consumer.jar"
}
