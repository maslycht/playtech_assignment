# Player Wallet Service

The player wallet service, which handles and stores player data and transactions sent from
[Gameplay Client](../gameplayclient/README.md).

## How to build and run

* (Option 1) Build with Maven and run locally:
  * `cd playerwalletservice`
  * `mvn package` - build and package the application into an executable JAR
    * alternatively, use `./mvnw package` (Linux) or `.\mvnw.cmd package` (Windows)
  * `java -jar target/playerwalletservice-0.0.1-SNAPSHOT.jar` - start the application with default parameters
  * custom properties can be configured in the `application.properties` file (prior to the build) or passed to the
  application at startup as arguments, e.g. `java -jar target/playerwalletservice-0.0.1-SNAPSHOT.jar --server.port=12345
  --balanceChangeLimit=100`
* (Option 2) Build and run in Docker:
  * `cd playerwalletservice`
  * `docker build -t playerwalletservice:latest .` - build the Docker image named "playerwalletservice"
  * `docker run -p 12345:8080 --name playerwalletservice playerwalletservice` - run a container named "playerwalletservice"
  from the previously built "playerwalletservice" image, publishing the container's port 8080 (default application port)
  to the host's port 12345
  * similarly to the local execution, custom properties can be passed to the docker container as well:
  `docker run -p 12345:54321 --name playerwalletservice playerwalletservice --server.port=54321 --balanceChangeLimit=100`
* (Option 3) Build and run along with Gameplay Client using `docker-compose` - see
[Gameplay Client](../gameplayclient/README.md).

To monitor server logs for a specific user (e.g. usaaf96), one could execute
`docker container logs playerwalletservice -f | grep usaaf96`

## Implementation details

* The server uses SQLite DB. The file name is `backup.db`. Flyway is used for DB migration management.
* There is a scheduled task running every 10 seconds that persists the data from memory into the database.
This includes the player data, and the history of requests and responses.
  * `PLAYER` table stores the player data.
  * `TRANSACTION_RESPONSE` table stores the history of requests and responses.
  * Player data and the last 1000 requests and responses are loaded from the database at startup.
* Player record is created on demand (if not exists) but ONLY after a successful transaction. A new player record will
not be stored if the transaction results in any error.
* Player blacklist resides in the same database as player data and transaction history. It is a simple list of
blacklisted usernames. The values are inserted into the database using Flyway migration and are loaded into the memory
at startup.
* Balance change limit is configurable and can be set with a runtime parameter `balanceChangeLimit` before build
or at startup, as described in the "build and run" section.
* The server logs the relevant fields of every request and response. The logs are written to the default output.
