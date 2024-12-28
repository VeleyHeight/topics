#FROM maven:3.9.9-eclipse-temurin-17-focal as builder
#WORKDIR /app
#COPY mvnw pom.xml
#COPY ./src ./src
#RUN mvn clean install -DskipTests

FROM eclipse-temurin:17.0.13_11-jre-ubi9-minimal
WORKDIR /app
EXPOSE 8080
COPY target/*.jar /app/*.jar
ENTRYPOINT ["java", "-jar", "/app/*.jar"]