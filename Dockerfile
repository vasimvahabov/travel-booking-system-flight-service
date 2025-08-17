FROM eclipse-temurin:21-jre AS extractor

WORKDIR /extracted

ARG BUILT_JAR_NAME=flight-service.jar
ARG BUILT_JAR_PATH=./build/libs/${BUILT_JAR_NAME}

COPY ${BUILT_JAR_PATH} .

RUN java -Djarmode=tools \
        -jar ${BUILT_JAR_NAME} \
        extract --layers --launcher

FROM eclipse-temurin:21-jre

RUN useradd flight_service
USER flight_service

WORKDIR /flight-service
ARG EXTRACTOR_EXTRACTED_JAR_PATH=/extracted/flight-service

COPY --from=extractor ${EXTRACTOR_EXTRACTED_JAR_PATH}/dependencies/ .
COPY --from=extractor ${EXTRACTOR_EXTRACTED_JAR_PATH}/spring-boot-loader/ .
COPY --from=extractor ${EXTRACTOR_EXTRACTED_JAR_PATH}/snapshot-dependencies/ .
COPY --from=extractor ${EXTRACTOR_EXTRACTED_JAR_PATH}/application/ .

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]