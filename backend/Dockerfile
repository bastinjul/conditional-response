FROM eclipse-temurin:17.0.8.1_1-jre-jammy
LABEL authors="bastinjul"

COPY target/*.jar backend.jar

ENTRYPOINT ["java", "-jar", "backend.jar"]