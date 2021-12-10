FROM openjdk:11

EXPOSE 3000

ADD hospital-main/target/hospital-main-1.0-SNAPSHOT.jar hospital-main-1.0-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/hospital-main-1.0-SNAPSHOT.jar"]