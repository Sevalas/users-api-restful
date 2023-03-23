FROM gradle:latest AS build
COPY --chown=gradle:gradle . /src
WORKDIR /src
ARG H2_DB_USERNAME=${H2_DB_USERNAME}
ARG H2_DB_PASSWORD=${H2_DB_PASSWORDN}
ARG JWT_SECRET=${JWT_SECRET}
RUN gradle build --no-daemon

FROM openjdk:17-jdk-slim

EXPOSE 8080

COPY --from=build /src/build/libs/*.jar /users-api-restrful.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-jar","/users-api-restrful.jar"]