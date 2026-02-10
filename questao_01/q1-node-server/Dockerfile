# syntax=docker/dockerfile:1

FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /workspace
COPY . .

RUN mvn -pl q1-node-server -am -DskipTests package \
  && JAR_PATH="$(ls q1-node-server/target/q1-node-server-*.jar | grep -v '\\.original$' | head -n 1)" \
  && cp "$JAR_PATH" /tmp/app.jar

FROM eclipse-temurin:21-jre

WORKDIR /app
COPY --from=build /tmp/app.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
