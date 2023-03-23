FROM gradle:latest AS build
COPY --chown=gradle:gradle . /src
WORKDIR /src
RUN gradle build --no-daemon

FROM openjdk:17-jdk-slim

EXPOSE 8080

COPY --from=build /src/build/libs/*.jar /users-api-restrful.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-jar","/users-api-restrful.jar"]