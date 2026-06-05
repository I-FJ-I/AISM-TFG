FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /app/
COPY pom.xml .
RUN mvn -e -B dependency:resolve
COPY src ./src
RUN mvn package

FROM eclipse-temurin:17-jre
COPY --from=builder /app/target/servicio-fhir-0.0.1-SNAPSHOT.jar /root.jar
CMD ["java", "-jar", "/root.jar"]

EXPOSE 8080