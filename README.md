# Require4Testing

Eine Web-Applikation zur Organisation manueller Anwendertests, entwickelt im Rahmen einer wissenschaftlichen Arbeit.

## Technologie-Stack

- **Backend:** Spring Boot 4.x, Spring Data JPA
- **ORM:** Hibernate 7
- **Datenbank:** MySQL 8
- **Frontend:** Thymeleaf
- **Build-Tool:** Maven

## Implementierte User Stories (Sprint 1)

| # | Rolle | User Story | Priorität |
|---|-------|-----------|-----------|
| US1 | Requirements Engineer | Anforderungen erstellen | MUST |
| US2 | Testmanager:in | Testläufe anlegen | MUST |
| US3 | Testfallersteller:in | Testfälle zu Anforderungen erstellen | MUST |
| US4 | Testmanager:in | Testlauf mit Testfällen und Tester:in verknüpfen | SHOULD |
| US5 | Tester:in | Testergebnis erfassen | SHOULD |

## Datenbankschema

```
requirements        testcases              testruns          testexecutions
-------------       ---------              --------          --------------
id (PK)             id (PK)                id (PK)           id (PK)
title               title                  name              testrun_id (FK)
description         description            createdAt         testcase_id (FK)
                    requirement_id (FK)                      tester
                                                             result (ENUM)
```

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
|-----|---------|
| `/requirements` | Anforderungen anzeigen und erstellen (US1) |
| `/testruns` | Testläufe anzeigen und anlegen (US2) |
| `/testcases` | Testfälle anzeigen und erstellen (US3) |
| `/executions` | Testdurchführungen zuordnen und Ergebnisse erfassen (US4, US5) |
