FROM maven:3.6.0-jdk-8-alpine

EXPOSE 8080

CMD mvn jetty:run