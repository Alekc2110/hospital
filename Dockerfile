FROM openjdk:11

EXPOSE 8085

WORKDIR /opt/hospital-test


COPY hospital-main/target/hospital-main-1.0-SNAPSHOT.jar /hospital-main-1.0-SNAPSHOT.jar

CMD ["java", "-jar", "/hospital-main-1.0-SNAPSHOT.jar"]
