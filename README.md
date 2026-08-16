# Require4Testing

Eine Web-Applikation zur Organisation manueller Anwendertests, entwickelt im Rahmen einer wissenschaftlichen Arbeit (Fallstudie, Modul IPWA02-01).

## Technologie-Stack

- **Sprache:** Java 17
- **Backend:** Spring Boot 4, Spring MVC, Spring Data JPA
- **ORM:** Hibernate (JPA-Provider)
- **Sicherheit:** Spring Security (rollenbasierte Zugriffskontrolle, bcrypt)
- **Validierung:** Bean Validation
- **Frontend:** Thymeleaf (serverseitiges Rendering)
- **Datenbank:** MariaDB (MySQL-kompatibel), lokal über XAMPP; JDBC-Treiber MySQL Connector/J
- **Build-Tool:** Maven

## Implementierte User Stories

Alle acht User Stories des Product Backlog sind umgesetzt (US1–US5 als Kern, US6–US8 ergänzend):

| # | Rolle | User Story | Priorität |
|---|-------|-----------|-----------|
| US1 | Requirements Engineer | Zu testende Anforderungen erstellen | MUST |
| US2 | Testmanager:in | Testläufe anlegen | MUST |
| US3 | Testfallersteller:in | Testfälle zu Anforderungen erstellen (n:m) | MUST |
| US4 | Testmanager:in | Testlauf mit Testfällen und Tester:in verknüpfen | SHOULD |
| US5 | Tester:in | Testergebnis je Schritt erfassen (mit Historie) | SHOULD |
| US6 | Tester:in | Überblick über zugewiesene Testfälle | COULD |
| US7 | Testmanager:in | Status-Dashboard aller Testdurchführungen | COULD |
| US8 | Testfallersteller:in | Einzelne Testschritte zu Testfällen erfassen | COULD |

## Architektur

Mehrschichtige Architektur im Basispaket `de.hochschule.require4testing`:

- `controller/` – Spring-MVC-Controller (Präsentationsschicht)
- `service/` – Geschäftslogik
- `repository/` – Spring-Data-JPA-Repositories (Persistenz)
- `entity/` – JPA-Entitäten und Enumerationen (Domänenmodell)
- `config/` – Sicherheits- und Initialisierungskonfiguration
- `security/` – Anmeldelogik (UserDetailsService)
- `dto/` – Objekte zur Aufbereitung von Auswertungsdaten

## Datenbankschema

Zehn Tabellen, inklusive zwei Verknüpfungstabellen für die n:m-Beziehungen:

- `users` (id, username, password_hash, enabled)
- `roles` (id, name)
- `user_roles` (user_id → users, role_id → roles) – n:m Benutzer/Rollen
- `requirements` (id, req_key, title, description, priority, status, acceptance_criteria, category)
- `testcases` (id, title, precondition, priority, type)
- `testcase_requirements` (testcase_id → testcases, requirement_id → requirements) – n:m Testfall/Anforderung
- `test_steps` (id, step_number, action, expected_result, testcase_id → testcases)
- `testruns` (id, name, description, planned_start, planned_end, status, target_environment, created_at)
- `testexecutions` (id, testrun_id → testruns, testcase_id → testcases, tester_id → users)
- `step_results` (id, status, comment, recorded_at, execution_id → testexecutions, step_id → test_steps, tested_by → users)

## Lokale Einrichtung und Anmeldung

### Voraussetzungen
- Java 17+
- XAMPP mit laufendem MySQL
- Maven (der mitgelieferte Wrapper `mvnw` genügt)

### Datenbank einrichten
Eine leere Datenbank anlegen – die Tabellen erzeugt Hibernate beim Start automatisch (`spring.jpa.hibernate.ddl-auto=update`):
```sql
CREATE DATABASE require4testing;
```

### Datenbank-Passwort
Das Passwort wird über die Umgebungsvariable `DB_PASSWORD` gelesen. Beim XAMPP-Standard (Benutzer `root` ohne Passwort) kann sie leer bleiben oder entfallen. Bei gesetztem MySQL-Passwort z. B. unter Windows (PowerShell):
```powershell
$env:DB_PASSWORD="deinPasswort"
```

### Starten
```bash
./mvnw spring-boot:run
```
Anschließend im Browser öffnen: `http://localhost:8080`

### Anmeldung
Der Zugriff ist geschützt; ohne Anmeldung wird auf die Login-Seite geleitet. Beim ersten Start wird automatisch ein Benutzer mit allen vier Rollen angelegt:

| Benutzername | Passwort |
|--------------|----------|
| `admin`      | `admin123` |

Dieser Benutzer kann den gesamten Funktionsumfang testen, einschließlich der rollengeschützten Bereiche **Dashboard** (`TEST_MANAGER`) und **Meine Testfälle** (`TESTER`).

## URLs

| URL | Funktion |
|-----|----------|
| `/login` | Anmeldung |
| `/` | Startseite |
| `/requirements` | Anforderungen anzeigen und erstellen (US1) |
| `/testruns` | Testläufe anlegen und Status ändern (US2) |
| `/testcases` | Testfälle anzeigen und erstellen (US3) |
| `/testcases/{id}` | Testfall-Detail mit Testschritten (US8) |
| `/executions` | Testfälle und Tester:in zuordnen, Testdurchführungen anzeigen (US4) |
| `/executions/{id}` | Ergebnis je Testschritt erfassen, mit Historie (US5) |
| `/executions/mine` | Eigene zugewiesene Testfälle (US6) |
| `/dashboard` | Status-Dashboard (US7) |