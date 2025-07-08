# ubb-eventapp-backend
Java backend for Event App

## Database Configuration

The application reads the MySQL connection details from environment variables. Create these variables before running the application:

- `DB_URL` – JDBC connection string, e.g. `jdbc:mysql://localhost:3306/eventdb`
- `DB_USERNAME` – database user
- `DB_PASSWORD` – database password

Defaults are provided for local development if the variables are not set.
