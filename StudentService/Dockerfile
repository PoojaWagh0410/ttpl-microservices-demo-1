FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/StudentService-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081
EXPOSE 5051

ENTRYPOINT ["java", "-jar", "app.jar"]
