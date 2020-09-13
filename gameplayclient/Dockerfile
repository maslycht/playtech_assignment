FROM openjdk:11
RUN apt-get update
RUN apt-get install -y maven
COPY pom.xml /usr/local/gameplayclient/pom.xml
COPY src /usr/local/gameplayclient/src
WORKDIR /usr/local/gameplayclient
RUN mvn package

ENTRYPOINT ["java","-jar","target/gameplayclient-0.0.1-SNAPSHOT.jar"]