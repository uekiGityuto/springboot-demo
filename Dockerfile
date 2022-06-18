FROM openjdk:17-jdk-slim-bullseye AS build-env
WORKDIR /app
COPY ./ ./
RUN ./mvnw package -DskipTests=true

FROM busybox:1.35-musl as busybox

FROM gcr.io/distroless/java17:nonroot
WORKDIR /app
# K8sのpreStopHookでsleepコマンドを使えるようにバイナリを配置しておく
COPY --from=busybox --chown=nonroot:nonroot  /bin/sleep /bin/sleep
COPY --from=build-env --chown=nonroot:nonroot /app/target/demo.jar ./demo.jar
COPY --chown=nonroot:nonroot ./javaagent/dd-java-agent.jar ./dd-java-agent.jar
ENTRYPOINT ["java", "-Duser.timezone=Asia/Tokyo", "-Dfile.encoding=UTF8", "-javaagent:./dd-java-agent.jar", "-jar", "./demo.jar"]
