plugins {
    java
    id("net.ltgt.errorprone") version "0.7.1"
}

repositories {
    jcenter()
}

allprojects {
    group = "kaizen7"
    version = "1.0-SNAPSHOT"
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    compileOnly("com.google.code.findbugs:jsr305:3.0.2")
    
    compile("com.google.code.gson:gson:2.8.5")

    // Logging.
    // logback-classic dependency pulls in slf4j automatically.
    compile("ch.qos.logback:logback-classic:1.2.3")

    // Test dependencies.
    testCompile("org.junit.jupiter:junit-jupiter:5.4.0")
    testCompile("org.mockito:mockito-core:2.24.5")
    testCompile("org.assertj:assertj-core:3.12.1")
}

configurations {
    implementation {
        resolutionStrategy.failOnVersionConflict()
    }
}
