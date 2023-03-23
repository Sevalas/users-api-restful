FROM gradle:latest AS build
COPY --chown=gradle:gradle . /src
WORKDIR /src
ARG H2_DB_USERNAME=user
ARG H2_DB_PASSWORD=user
ARG JWT_SECRET=dsagsdgsdmgnwekgnwekjhgnwekhnkjhnugnwegnwekgnwakjgnwkajgnwiugnweoiungwegwe
RUN gradle build --no-daemon

FROM openjdk:17-jdk-slim

COPY --from=build /src/build/libs/*.jar /users-api-restrful.jar

CMD [ "sh", "-c", "java $JAVA_OPTS -Dserver.port=$PORT -Djava.security.egd=file:/dev/./urandom -jar /users-api-restrful.jar" ]