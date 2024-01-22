FROM openjdk:17-jdk-alpine
COPY src src
COPY target/f1-prediction-game.jar app.jar
ENV SEASON=2023
ENV RACE=1
ENTRYPOINT java -jar /app.jar -season ${SEASON} -race ${RACE}