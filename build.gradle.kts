plugins {
	java
	id("org.springframework.boot") version "3.5.3"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.travelbookingsystem"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		// languageVersion = JavaLanguageVersion.of(21)
		languageVersion = JavaLanguageVersion.of(24)
	}
}

repositories {
	mavenCentral()
}

dependencies {

	/* Lombok */
	compileOnly("org.projectlombok:lombok:1.18.38")
	annotationProcessor("org.projectlombok:lombok:1.18.38")

	testCompileOnly("org.projectlombok:lombok:1.18.38")
	testAnnotationProcessor("org.projectlombok:lombok:1.18.38")
	/* Lombok */

	/*Spring Boot*/
	implementation("org.springframework.boot:spring-boot-starter-web")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	/*Spring Boot*/

	/* Java Bean Validation API */
	implementation("org.springframework.boot:spring-boot-starter-validation")
	/* Java Bean Validation API */

}

tasks.withType<Test> {
	useJUnitPlatform()
}