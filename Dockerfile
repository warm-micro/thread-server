FROM openjdk:11

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} thread-0.0.1-SNAPSHOT.jar

EXPOSE 50056

ENTRYPOINT ["java","-jar","/thread-0.0.1-SNAPSHOT.jar"]