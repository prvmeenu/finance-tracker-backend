FROM eclipse-temurin:21-jdk
LABEL authors="prvm"

WORKDIR /app

COPY target/FinanceTracker-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]