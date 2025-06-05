# 🗺️ SquadMap – Live-Standorte deiner Squad auf einer Karte

**SquadMap** ist eine modulare Echtzeit-Standortkarte für Gruppen, die nach dem KISS-Prinzip entwickelt wurde. Sie erlaubt es, die Positionen mehrerer Nutzer:innen live zu visualisieren. Die Architektur folgt dem **Domain Driven Design**, ist **Microservice-fähig** und setzt auf eine klare Trennung von Verantwortlichkeiten mit bewährten GoF-Entwurfsmustern.

## ⚙️ Technologien

### Backend
- **Java 21 mit Quarkus**
- **JPA/Hibernate** für ORM
- **Flyway** für Migrations
- **MySQL/MariaDB**
- REST-API via **JAX-RS**
- Modular aufgebaut: `location`, `user`, etc.

### Frontend
- **JavaScript** mit **Vite**
- **Leaflet.js** für die Kartenanzeige
- Kommunikation via **REST**

### DevOps
- **Docker/Compose** für Containerisierung
- **CI/CD** via **GitHub Actions**
- Automatisiertes Deployment auf Staging (Hetzner)

## 🧱 Aufbau

```
squadmap/
├── backend/
│   ├── src/
│   │   ├── main/java/org/squadmap/location/
│   │   └── resources/db/migration/
│   └── Dockerfile
├── frontend/
│   └── ...
├── .github/workflows/
│   └── deploy-staging.yml
└── README.md
```

## 🚀 Projekt starten

### Staging Deployment via GitHub Actions

1. Repository clonen
2. SSH Key unter GitHub als Secret setzen:
   - `STAGING_HOST`, `STAGING_USER`, `STAGING_SSH_KEY`, `STAGING_PORT`
3. Push auf `main` triggert CI/CD

### Manuell lokal

```bash
cd backend
./mvnw clean package -Dquarkus.package.type=uber-jar
docker-compose up --build
```

## 🔐 .env Handling

Die `.env`-Datei wird für sensible Variablen wie DB-Credentials genutzt. Sie wird vom Deployment automatisch übernommen und muss auf dem Zielsystem vorhanden sein.

## 🧩 Erweiterbarkeit

Das System ist darauf ausgelegt, einfach um neue Microservices ergänzt zu werden, z. B.:

- Authentifizierungsservice (Keycloak/JWT)
- Notification-Service (WebPush, Email)
- Map-Overlay-Service für Heatmaps oder Routen

## ✨ Features (Geplant)

- Auth via OAuth2
- WebSocket für echte Live-Updates
- Rollenbasierte Berechtigungen (Squad-Admin, Viewer)
- Dark Mode für Map
- Mobile App (Flutter)

## 🧠 Autoren

- Ivan V. Babayev ([@eternal-pingdom](https://github.com/eternal-pingdom))

## 📄 Lizenz

MIT License – siehe [LICENSE](./LICENSE)