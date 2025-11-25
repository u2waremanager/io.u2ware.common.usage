FROM openjdk:24-ea-17-jdk-slim-bullseye

# # WORKDIR /workspace

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} /app.jar

# # ENV SPRING_PROFILES_ACTIVE="dev"
ENV PORT="8080"
EXPOSE ${PORT}

ENTRYPOINT ["java","-jar", "/app.jar"]

LABEL org.opencontainers.image.source=https://github.com/u2waremanager/io.u2ware.examples.rest