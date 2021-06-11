FROM openjdk:8u191-jdk-alpine3.9
ADD target/movieapp.jar .
EXPOSE 8080
CMD java -jar movieapp.jar
