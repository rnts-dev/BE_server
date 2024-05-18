FROM eclipse-temurin:17-jdk AS builder

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

RUN chmod +x ./gradlew
RUN ./gradlew bootJar

FROM eclipse-temurin:17-jre
COPY --from=builder build/libs/*.jar app.jar
COPY src/main/resources/keystore.p12 /app/keystore.p12

EXPOSE 8443

CMD ["java", "-jar", "app.jar"]