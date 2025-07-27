plugins {
	java
	id("org.springframework.boot") version "3.5.4"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.travelbookingsystem"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

val lombokVersion = "1.18.38"

dependencies {

	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")

	compileOnly("org.projectlombok:lombok:$lombokVersion")

	annotationProcessor("org.projectlombok:lombok:$lombokVersion")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.boot:spring-boot-starter-webflux")

	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	testCompileOnly("org.projectlombok:lombok:$lombokVersion")

	testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")
}

//tasks.named<org.springframework.boot.gradle.tasks.run.BootRun>("bootRun") {
//	systemProperty("spring.profiles.active", "test-data")
//}

tasks.withType<Test> {
	useJUnitPlatform()
}