FROM openjdk:11.0.11-jre
ADD target/application-review-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT exec java -jar app.jar