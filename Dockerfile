FROM java:8
ADD gs-rest-service-0.1.0.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]