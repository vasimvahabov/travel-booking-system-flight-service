FROM eclipse-temurin:21-jre AS extractor

WORKDIR /extracted

ARG BUILT_JAR_PATH="./build/libs/flight-service-0.1.0.jar"

COPY ${BUILT_JAR_PATH} .

RUN java -Djarmode=tools \
        -jar flight-service-0.1.0.jar \
        extract --layers --launcher

FROM eclipse-temurin:21-jre

RUN useradd flight_service
USER flight_service

WORKDIR /flight-service

COPY --from=extractor /extracted/flight-service-0.1.0/dependencies/ .
COPY --from=extractor /extracted/flight-service-0.1.0/spring-boot-loader/ .
COPY --from=extractor /extracted/flight-service-0.1.0/snapshot-dependencies/ .
COPY --from=extractor /extracted/flight-service-0.1.0/application/ .

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]