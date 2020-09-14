# Gameplay Client

A mockup of a gameplay client, which generates random transactions to test the
[Player Wallet Service](../playerwalletservice/README.md).

## How to build and run

* (Option 1) Build with Maven and run locally:
  * `cd gameplayclient`
  * `mvn package` - build and package the application into an executable JAR
    * alternatively, use `./mvnw package` (Linux) or `.\mvnw.cmd package` (Windows)
  * `java -jar target/gameplayclient-0.0.1-SNAPSHOT.jar` - start the application with default parameters
  * custom Player Wallet Service hostname `playerwalletservice.hostname` and port `playerwalletservice.port` can be set
  as environment variables `PLAYERWALLETSERVICE_HOSTNAME` and `PLAYERWALLETSERVICE_PORT` respectively or passed to the
  application at startup as arguments, e.g. `java -jar target/gameplayclient-0.0.1-SNAPSHOT.jar
  --playerwalletservice.hostname=playerwalletservice --playerwalletservice.port=12345`
* (Option 2) Build and run in Docker:
  * `cd gameplayclient`
  * `docker build -t gameplayclient:latest .` - build the Docker image named "gameplayclient"
  * `docker run --name gameplayclient gameplayclient` - run a container named "gameplayclient" from the previously built
  "gameplayclient" image
  * similarly to the local execution, custom properties can be passed to the docker container as well:
  `docker run --name gameplayclient gameplayclient --playerwalletservice.hostname=playerwalletservice
  --playerwalletservice.port=12345`
* (Option 3) Build and run along with Player Wallet Service using `docker-compose`:
  * _commands executed from the root dir:_
  * `docker build -t playerwalletservice:latest ./playerwalletservice/` - build the Player Wallet
  Service Docker image
  * `docker build -t gameplayclient:latest ./gameplayclient/` - build the Gameplay Client Docker
  image
  * `docker-compose -f gameplayclient/docker-compose.yml up` - run both containers at once and
  connect them directly using a Docker Network

## Implementation Details

The application takes a hardcoded list of 10 usernames. There is a scheduled task that runs every second and generates a
transaction for a random user from the list and a random balance change amount and sends that transaction to the Player
Wallet Service for processing. The application logs every request and response similarly to the Service.