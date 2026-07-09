# Start with a base image contaning Java Runtime
FROM eclipse-temurin:17-jdk-jammy

MAINTAINER ompeshpadmpalle

#Add the appliation jar to image
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

#Execute the application
ENTRYPOINT ["java","-jar","accounts-0.0.1-SNAPSHOT.jar"]