FROM openjdk:17-alpine as builder
COPY . /app
WORKDIR /app
RUN ./gradlew build

FROM openjdk:17-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/controle-financeiro.jar .
ENTRYPOINT ["java", "-jar", "./controle-financeiro.jar"]
