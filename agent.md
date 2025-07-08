# agent.md – Java 17 build & smoke‑test recipe

> **Purpose**  Automate build, unit‑test execution, application startup, and a minimal health‑check for any Maven‑based Java 17 project. Copy or symlink this file to your repository root so every agent (local CLI, CI runner, or Gen‑AI) knows exactly how to get the project running.

---

## 1  Environment

```bash
# 1.1 Install & select an OpenJDK 17 distribution.
# Recommended in CI: use SDKMAN or distribution‑specific package.
# Example with sdkman (works locally and in most Linux images):
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"

sdk install java 17.0.10-tem # or your preferred vendor
sdk use java 17.0.10-tem

# 1.2 Verify version
java -version    # should print 17.x
mvn  -v          # Maven ≥3.9.x required (wrapper will self‑install)
```

> **Tip:** The project ships with `mvnw` / `mvnw.cmd`, so Maven will bootstrap itself if absent.

---

## 2  Build & Test

```bash
# Fail fast and show full logs
set -euo pipefail

# 2.1 Clean, compile, run unit & integration tests
./mvnw -B -V clean verify
```

*Artifacts*

- Compiled JAR files live under `target/`
- Test reports: `target/surefire-reports/` and `target/failsafe-reports/`

---

## 3  Run Application

```bash
# 3.1 Locate runnable jar (fat‑jar or Spring Boot)
JAR_FILE="$(ls -1 target/*.jar | head -n 1)"

# 3.2 Launch in background
java -jar "$JAR_FILE" &
APP_PID=$!

# 3.3 Wait for HTTP port (8080 by default) to accept connections
TIMEOUT=60       # seconds
HEALTH_URL=${HEALTH_URL:-http://localhost:8080/actuator/health}

(
  until curl -fsSL "$HEALTH_URL" | grep -q '"status":"UP"'; do
    printf '.'
    sleep 2
    TIMEOUT=$((TIMEOUT-2))
    [ $TIMEOUT -le 0 ] && { echo "\nTimed out waiting for health‑check"; exit 1; }
  done
)

echo "\n✅ Application is UP & healthy"
```

---

## 4  Tear Down

```bash
kill "$APP_PID" || true
wait "$APP_PID" 2>/dev/null || true
```

---

## 5  CI minimal example (GitHub Actions)

```yaml
name: Java 17 CI

on:
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]

jobs:
  build-and-test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - name: Set up JDK 17
        uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: '17'
          cache: maven
      - name: Build, Test & Smoke‑Test
        run: |
          bash agent.md  # GitHub automatically sets bash as the shell
```

---

## 6  Exit Codes

| Code | Meaning                                     |
| ---- | ------------------------------------------- |
| 0    | Build, tests, and health‑check passed.      |
| 1    | Build/tests failed or health‑check timeout. |

---

## 7  Customization Hooks

| Variable     | Default                                 | Purpose                              |
| ------------ | --------------------------------------- | ------------------------------------ |
| `HEALTH_URL` | `http://localhost:8080/actuator/health` | URL to poll for healthy status.      |
| `TIMEOUT`    | `60`                                    | Seconds to wait for the app to boot. |
| `MVN_PROPS`  | *(none)*                                | Extra `-D` properties for Maven.     |

---

## 8  Troubleshooting

| Symptom                                  | Likely cause & remedy                                         |
| ---------------------------------------- | ------------------------------------------------------------- |
| `java.lang.UnsupportedClassVersionError` | Running on wrong JDK. Confirm `java -version` is 17.          |
| Health check never returns               | Wrong port/env profile. Set `HEALTH_URL` accordingly.         |
| Tests hang in CI                         | Missing headless browser lib? Add `-Djava.awt.headless=true`. |

---

## 9  License

Distributed under the MIT License. Copy, modify, or adapt as needed.

