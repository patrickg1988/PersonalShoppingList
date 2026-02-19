# PersonalShoppingList 🛒 (Spring Boot)

Eine **persönliche Einkaufsliste** als **Lern- und Demo-Projekt** mit **Spring Boot**, **Spring MVC**, **Thymeleaf**, **Spring Security** und **H2 + JPA**.

Im Fokus stehen typische Grundlagen moderner Java-Webentwicklung:

- MVC (Controller → Service → View)
- Formularverarbeitung (GET/POST)
- Login/Logout mit Spring Security
- Persistenz mit Spring Data JPA
- Geldbeträge korrekt mit `BigDecimal`

> Hinweis: Die HTML-Templates sind bewusst simpel gehalten und eignen sich gut zum schrittweisen Ausbau im Unterricht.

---

## ✨ Features

- **Registrierung & Login**
  - `GET /register` (Formular) / `POST /register` (Account anlegen)
  - `GET /login` (Login-Seite, Spring Security Form-Login)
- **Geschützte Einkaufsliste**
  - `GET /einkaufsliste` → zeigt die persönliche Liste des eingeloggten Users
  - `POST /artikel` → Artikel hinzufügen
  - `POST /artikel/loeschen` → Artikel löschen (nur eigene Artikel)
- **Pro Benutzer getrennte Daten**
  - Artikel speichern nur eine `ownerId` (bewusst ohne `@ManyToOne`, damit es für Schüler einfacher bleibt)
- **H2-Datenbank + JPA**
  - File-DB (persistiert lokal im Ordner `./data/`)
  - H2 Console aktiviert: `/h2-console`
- **Passwort-Hashing**
  - BCrypt via `PasswordEncoder`

---

## 🧰 Tech-Stack

- Java **17**
- Spring Boot **4.0.1**
- Spring Web (MVC)
- Thymeleaf (+ Extras für Spring Security)
- Spring Security (Form Login)
- Spring Data JPA
- H2 Database
- Lombok

---

## 📦 Projektstruktur (wichtigste Bereiche)

> Das eigentliche Projekt liegt im Unterordner `demo/`.

```
PersonalShoppingList
└── demo
    ├── src/main/java/com/example/demo
    │   ├── controller/   # MVC-Controller (Login/Register/Einkaufsliste)
    │   ├── model/        # JPA-Entities (AppUser, Artikel)
    │   ├── repository/   # Spring Data Repositories
    │   ├── security/     # SecurityConfig + PasswordEncoder
    │   └── service/      # Business-Logik (UserService, EinkaufService)
    └── src/main/resources
        ├── templates/    # Thymeleaf Views (index, login, register, einkaufsliste)
        └── application.properties
```

---

## 🚀 Quickstart

### 1) Starten

```bash
cd demo
./mvnw spring-boot:run
# oder:
mvn spring-boot:run
```

### 2) Öffnen

Standard-Port (laut `application.properties`):

- http://localhost:8081

---

## 🔐 Login / Registrierung

### Registrierung

- Öffne: `http://localhost:8081/register`
- Lege einen neuen Benutzer an
- Danach weiter zu: `http://localhost:8081/login`

### Login

- Öffne: `http://localhost:8081/login`
- Nach erfolgreichem Login kannst du auf die geschützte Liste zugreifen:
  - `http://localhost:8081/einkaufsliste`

---

## 🗄️ Datenbank & H2 Console

### H2 Console

- URL: `http://localhost:8081/h2-console`

In den Properties ist eine Datei-Datenbank konfiguriert (persistiert lokal):

- JDBC URL (Beispiel): `jdbc:h2:file:./data/appdb;MODE=PostgreSQL;AUTO_SERVER=TRUE`

> Tipp für Unterricht: Wenn ihr keine persistente DB wollt, könnt ihr später auf `jdbc:h2:mem:testdb` umstellen.

---

## 🧠 Didaktische Hinweise (für Schüler)

### Was ist hier „MVC“?

- **Controller**: nimmt HTTP-Anfragen an (z. B. `GET /einkaufsliste`)
- **Service**: enthält Logik (z. B. Gesamtpreis berechnen, OwnerId ermitteln)
- **View (Thymeleaf)**: zeigt HTML-Seite an und rendert Daten aus dem Model

### Warum `BigDecimal` für Preise?

Geldbeträge sollten nicht mit `double` berechnet werden, weil `double` Rundungsfehler erzeugen kann.  
`BigDecimal` ist dafür der Standard in Java.

### Warum `ownerId` statt `@ManyToOne`?

Für Lernprojekte ist es oft einfacher, Beziehungen zunächst **logisch** (über IDs) zu modellieren, bevor man JPA-Relationen einführt.

---

## ✅ Typische Erweiterungen (Übungsaufgaben)

1. **Validierung**
   - Name darf nicht leer sein
   - Menge muss > 0 sein
   - Preis muss ≥ 0 sein

2. **Bearbeiten-Funktion**
   - Artikel ändern (Menge/Preis)

3. **Kategorien**
   - z. B. Obst, Milchprodukte, Drogerie …

4. **Mehrbenutzer-Features**
   - Rollen (ADMIN/USER)
   - Admin-Übersicht

5. **JPA-Relationen**
   - `Artikel` ↔ `AppUser` mit `@ManyToOne`

---

## ⚠️ Hinweis zu Demo-Credentials

In `application.properties` stehen auch `spring.security.user.*` (für einfache Demos).  
In diesem Projekt wird jedoch ein **eigenes `UserDetailsService`** verwendet, das Benutzer aus der **Datenbank** lädt.

➡️ Praxis: Nutze am besten die **Registrierung** (`/register`), um Accounts anzulegen.

---

## 📄 Lizenz

MIT License (siehe `LICENSE`).

---

Viel Spaß beim Lernen und Erweitern! 🚀
