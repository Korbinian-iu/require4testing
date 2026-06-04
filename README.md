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

## Lokale Einrichtung

### Voraussetzungen
- Java 17+
- Maven
- MySQL 8

### Datenbank einrichten
```sql
CREATE DATABASE require4testing;
```

### Konfiguration
Datei `src/main/resources/application.properties` anpassen:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/require4testing
spring.datasource.username=root
spring.datasource.password=DEIN_PASSWORT
```

### Starten
```bash
./mvnw spring-boot:run
```

App erreichbar unter: `http://localhost:8080`

## URLs

| URL | Funktion |
|-----|---------|
| `/requirements` | Anforderungen anzeigen und erstellen (US1) |
| `/testruns` | Testläufe anzeigen und anlegen (US2) |
| `/testcases` | Testfälle anzeigen und erstellen (US3) |
| `/executions` | Testdurchführungen zuordnen und Ergebnisse erfassen (US4, US5) |
