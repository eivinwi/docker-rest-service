FROM java:8
ADD target/docker-rest-service-0.1.0.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
