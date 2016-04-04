FROM java:8
VOLUME /tmp
ADD gs-rest-service-0.1.0.jar app.jar
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005", "-jar", "/app.jar"]

#mvn clean install && cp target/gs-rest-service-0.1.0.jar docker/ && mvn package docker:build && docker save dockerserver/gs-rest-service > ~/docserv
