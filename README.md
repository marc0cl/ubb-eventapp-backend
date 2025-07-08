# ubb-eventapp-backend
Java backend for Event App

## Database Configuration
The application reads MySQL connection details from environment variables:

- `DB_URL` – JDBC connection string, e.g. `jdbc:mysql://localhost:3306/eventdb`
- `DB_USERNAME` – database user
- `DB_PASSWORD` – database password

Defaults are provided for local development if the variables are not set.

## API Testing with Postman
A Postman collection with all endpoints is provided under the `postman` folder. Import `postman/EventApp.postman_collection.json` into Postman and set the `baseUrl` variable to match your running server (e.g. `http://localhost:8080`).

Defaults are provided for local development if not set.

## Building & Testing
Run the Maven build and tests using:

```bash
mvn -B clean verify
```

A standard Java 17 JDK and Maven 3.8+ are required. See `agent.md` for a full build recipe.

## Documentation
Detailed table definitions can be found in [docs/DATABASE.md](docs/DATABASE.md).