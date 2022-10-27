FROM maven:latest AS build

RUN mkdir /artefact
COPY . /artefact
WORKDIR /artefact
RUN mvn clean package -DskipTests

FROM openjdk:17-ea-alpine

WORKDIR /app
COPY --from=build /artefact/target/*.jar *.jar

ENTRYPOINT ["java","-jar","*.jar"]

EXPOSE 8080