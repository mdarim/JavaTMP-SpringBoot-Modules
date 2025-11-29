## FROM openjdk:8-jdk-alpine
# Use the official Oracle OpenJDK 23 image as the base
FROM container-registry.oracle.com/java/openjdk:23
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
