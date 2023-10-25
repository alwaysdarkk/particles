import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.0"
}

group = "com.github.alwaysdarkk"
version = "1.0.0"

repositories {
    mavenCentral()

    maven(url = "https://jitpack.io")
    maven(url = "https://repo.papermc.io/repository/maven-public/")
    maven(url = "https://oss.sonatype.org/content/groups/public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.2-R0.1-SNAPSHOT")
    compileOnly("org.jetbrains:annotations:24.0.1")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")

    implementation("com.github.HenryFabio:sql-provider:9561f20fd2")
    implementation("com.github.DevNatan.inventory-framework:inventory-framework:2.5.4-rc.1")

    val lombok = "org.projectlombok:lombok:1.18.26"
    compileOnly(lombok)
    annotationProcessor(lombok)
}

configurations.all {
    resolutionStrategy.cacheChangingModulesFor(0, TimeUnit.SECONDS)
}

tasks.withType<ShadowJar> {
    archiveClassifier.set("")
    archiveFileName.set("${project.name}-${version}.jar")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    sourceCompatibility = "17"
    targetCompatibility = "17"
}