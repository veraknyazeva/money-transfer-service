FROM openjdk:17-oracle

EXPOSE 5500

ADD target/money-transfer-service-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]