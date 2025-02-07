FROM openjdk:17-jdk

ARG JAR_FILE=build/libs/persist-0.0.1-SNAPSHOT.jar
ARG GEOLITE_FILE=src/main/resources/db/GeoLite2-Country.mmdb

COPY ${JAR_FILE} /persist.jar
COPY ${GEOLITE_FILE} /db/GeoLite2-Country.mmdb

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "-Duser.timezone=Asia/Seoul", "/persist.jar"]