# Installatiehandleiding: Sweat Daddy API

![Installatie](docs/sweatdaddy_header_image.png)

## Inhoudsopgave

1. [Inleiding](#inleiding)
2. [Projectstructuur](#projectstructuur)
3. [Gebruikte Technieken](#gebruikte-technieken)
4. [Installatie & Configuratie](#installatie--configuratie)
5. [Tests Uitvoeren](#tests-uitvoeren)
6. [Gebruikers & Autorisatie](#gebruikers--autorisatie)

---

## Inleiding

Sweat Daddy is een softwareoplossing voor personal trainers die de voortgang van hun cliënten willen
bijhouden. Als trainer kan je registreren en inloggen, cliëntprofielen maken, workouts creëren en deze
koppelen aan cliënten, en oefeningen toevoegen aan de exercise list. De API bevat beveiligde endpoints voor
gebruikers met de 'Trainer' en 'Client' rol.
Het doel van de applicatie is om het bijhouden van de voortgang van cliënten leuker en gemakkelijker te maken.
---

## Projectstructuur

Het project volgt de volgende architectuur:

- `nl.sweatdaddy.common`: Algemene configuratie, security en exception handling
- `nl.sweatdaddy.client`: Beheer van cliëntgegevens
- `nl.sweatdaddy.exercise`: Beheer van de oefeningenbibliotheek
- `nl.sweatdaddy.workout`: Beheer van workouts
- `nl.sweatdaddy.fileUpload`: File up- en download

Per domein is de structuur onderverdeeld in `controller`, `service`, `repository`, `entity` en `dto`.

---

## Gebruikte technieken

- **Java 21**: de programmeertaal en versie van de applicatie
- **Spring Boot 4.0.2**: het framework voor de API en security
- **Maven**: voor projectbeheer en dependencies
- **PostgreSQL**: de relationele database voor productie en ontwikkeling
- **H2 Database**: in-memory database voor testdoeleinden
- **Keycloak**: OAuth2 en JWT provider voor authenticatie en autorisatie
- **Spring Data JPA en Hibernate**: voor database-interactie

---

## Installatie en configuratie

### Benodigdheden

- Java 21 JDK
- Maven
- Een draaiende PostgreSQL database (of switch naar H2 in `application.properties`)
- Een Keycloak server (draaiend op poort 9090)

### Stappenplan lokaal opzetten

1. **Clone de repository**:
   ```bash
   git clone https://github.com/sjoerdjansz/eindopdracht-novi-backend
   cd eindopdracht-novi-backend

2. **Database configuratie**:
   Pas de database instellingen aan in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
   spring.datasource.username=jouw_username
   spring.datasource.password=jouw_password
   ```
3. **Keycloak configuratie**:
   Zorg dat je Keycloak realm en client overeenkomen met:
    - Issuer: `http://localhost:9090/realms/test`
    - Client ID: `sweatdaddy`


4. **Applicatie builden**:
   ```bash
   mvn clean install
   ```

5. **Applicatie runnen**:
   ```bash
   mvn spring-boot:run
   ```
   De API is nu bereikbaar op `http://localhost:8080`.

---

## Tests uitvoeren

De applicatie maakt gebruik van **JUnit 5**, **Mockito** en **WebMvcTest**.

- Om alle tests uit te voeren: `mvn test`
- De tests bevinden zich in de map `src/test/java`.
- Er zijn integratietests voor controllers en unit-tests voor de service-lagen met volledige dekking.

---

## Gebruik van endpoints via Postman collectie (JSON) of SwaggerUI

Importeer de bijgevoegde Trainer en Client collections in Postman om gebruik te maken van de verschillende
endpoints en/of gebruik de SwaggerUI link: `http://localhost:8080/swagger-ui.html`

---

## Test gebruikers en user-rollen

Voor de applicatie zijn twee typen gebruikers gemaakt, namelijk: Trainers en Clients. De applicatie is met
name geschreven vanuit het oogpunt en gebruik van de Trainer. De rol van de Client is vooralsnog kleiner.

**De inloggegevens van beide gebruikers/rollen:**

**Trainer (full access)**

- Naam: `testuser`
- Wachtwoord: `test`

**Client (limited access)**

- Naam: `testclient`
- Wachtwoord: `test`
- Email: `testclient@sweatdaddy.com`

---

## API documentatie

Onderstaand is een beknopt overzicht van slechts enkele van de API endpoints. Voor een volledig overzicht,
meer informatie en benodigde gegevens is het raadzaam om de volledige API Documentatie te lezen of de
bovenstaande SwaggerUI link te gebruiken.

| Methode  | Endpoint                                 | Rol            | Beschrijving                                                 |
|:---------|:-----------------------------------------|:---------------|:-------------------------------------------------------------|
| **GET**  | `/clients/me`                            | Client/Trainer | Haalt het profiel op van de momenteel 'ingelogde' gebruiker. |
| **GET**  | `/exercises`                             | Client/Trainer | Toont een overzicht van alle beschikbare oefeningen.         |
| **GET**  | `/clients`                               | Trainer        | Haalt een lijst op van alle geregistreerde cliënten.         |
| **POST** | `/clients`                               | Trainer        | Maakt een nieuwe cliënt aan.                                 |
| **GET**  | `/workouts`                              | Trainer        | Haal alle gemaakte workouts op.                              |
| **POST** | `/clients/{id}/profile-picture`          | Trainer        | Uploadt een profielfoto voor een specifieke cliënt.          |
| **GET**  | `/clients/{id}/profile-picture/download` | Client/Trainer | Downloadt de profielfoto als afbeelding.                     |