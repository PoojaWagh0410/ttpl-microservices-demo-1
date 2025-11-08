FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/ApiGateway-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
EXPOSE 5050

ENTRYPOINT ["java", "-jar", "app.jar"]
