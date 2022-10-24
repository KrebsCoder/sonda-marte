FROM maven:3.8.6-eclipse-temurin-11 AS build

RUN mkdir /artefact
COPY . /artefact
WORKDIR /artefact
RUN mvn clean package -DskipTests

FROM adoptopenjdk/openjdk11:alpine-jre

WORKDIR /app
COPY --from=build /artefact/target/probe.jar probe.jar

ENTRYPOINT ["java","-jar","probe.jar"]

EXPOSE 8080