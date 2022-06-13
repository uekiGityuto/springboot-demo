FROM openjdk:17-jdk-slim-bullseye AS build-env
WORKDIR /app
COPY ./ ./
RUN ./mvnw package -DskipTests=true

FROM gcr.io/distroless/java17:nonroot
WORKDIR /app
COPY --from=build-env --chown=nonroot:nonroot /app/target/demo.jar ./demo.jar
ENTRYPOINT ["java", "-jar", "./demo.jar"]
