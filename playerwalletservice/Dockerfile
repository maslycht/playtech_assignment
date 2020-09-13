FROM openjdk:11
RUN apt-get update
RUN apt-get install -y maven
COPY pom.xml /usr/local/playerwalletservice/pom.xml
COPY src /usr/local/playerwalletservice/src
WORKDIR /usr/local/playerwalletservice
RUN mvn package

EXPOSE 8080/tcp

CMD ["java","-jar","target/playerwalletservice-0.0.1-SNAPSHOT.jar"]