# Bingo

Multiplayer-Bingo-Game

Set up a game, invite your friends and pick random numbers

Demo: http://hotbingo.net/

## Techstack 

Maven-Multiproject
Backend: Java, Spring Boot & DynamoDb
Frontend: Angular


## build app.jar
mvn clean install
java -jar ./backend/target/bingo-backend-0.0.1-SNAPSHOT.jar

## Prerequisite
Requires an AWS-DynamoDB for Document-Storage
Create Table 'Bingo-Spiel' and set the edit the config in technology.scholz.bingo.config.DynamoConfigAws.java
