FROM maven:3.8.3-openjdk-17 as builder
COPY . /app
WORKDIR /app
RUN mvn clean install -DskipTests

FROM openjdk:17-alpine
WORKDIR /app
COPY --from=builder /app/infrastructure/target/infrastructure-1.0-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "./infrastructure-1.0-SNAPSHOT.jar"]
