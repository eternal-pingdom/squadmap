# SquadMap Backend (Quarkus)

Dies ist der Quarkus-basierte Backend-Service zur Standortverwaltung (location-service).  
Verwendet: Quarkus, JPA, Flyway, REST, Docker.

## Starten mit Docker

1. MySQL starten (z. B. via Docker Compose)
2. Dieses Projekt bauen: `./mvnw package`
3. Image bauen: `docker build -t squadmap-backend .`
4. Container starten: `docker run -p 8080:8080 squadmap-backend`
# test
