FROM openjdk:22-jdk-slim
WORKDIR /app
COPY ../movimientos-container-services/target/movimientos-container-services-1.0.0-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]