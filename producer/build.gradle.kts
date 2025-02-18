import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    implementation(project(":common"))

    implementation("org.springframework.boot:spring-boot-starter-web")
}

tasks.withType<BootJar> {
    archiveFileName = "pct-producer.jar"
}
