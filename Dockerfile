FROM openjdk:17-jdk

ARG JAR_FILE=build/libs/persist-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} /persist.jar

ENTRYPOINT ["sh", "-c", "java", "-jar", "-Dspring.profiles.active=prod", "/practice.jar", ">", "/logs/application.log", "2>&1"]