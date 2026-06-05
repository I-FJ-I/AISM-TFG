FROM maven:3.8.5-jdk-8 AS builder

WORKDIR /app/
COPY pom.xml .
RUN mvn -e -B dependency:resolve
COPY src ./src
RUN mvn package

FROM eclipse-temurin:8-jre
COPY --from=builder /app/target/servicio-etl-0.0.1-SNAPSHOT.jar /root.jar
CMD ["java", "-jar", "/root.jar"]

EXPOSE 8080