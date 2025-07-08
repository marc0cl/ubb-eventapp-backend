# ubb-eventapp-backend
Java backend for Event App

## Database Configuration
The application reads MySQL connection details from environment variables:

- `DB_URL` – JDBC connection string, e.g. `jdbc:mysql://localhost:3306/eventdb`
- `DB_USERNAME` – database user
- `DB_PASSWORD` – database password

Defaults are provided for local development if not set.

## Building & Testing
Run the Maven build and tests using:

```bash
mvn -B clean verify
```

A standard Java 17 JDK and Maven 3.8+ are required. See `agent.md` for a full build recipe.

## Documentation
Detailed table definitions can be found in [docs/DATABASE.md](docs/DATABASE.md).
