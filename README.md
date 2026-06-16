# BookTrack

BookTrack je web aplikacija za upravljanje mini knjižnicom izrađena korištenjem Spring Boot, Thymeleaf i MySQL tehnologija.

## Funkcionalnosti

### Upravljanje knjigama

* pregled svih knjiga
* pregled detalja knjige
* dodavanje nove knjige
* uređivanje postojeće knjige
* brisanje knjige
* pretraga knjiga po naslovu i autoru
* filtriranje po kategoriji i dostupnosti
* sortiranje knjiga

### Upravljanje kategorijama

* pregled svih kategorija
* dodavanje kategorija
* uređivanje kategorija
* brisanje kategorija
* prikaz broja knjiga po kategoriji

### Upravljanje posudbama

* evidencija posudbi knjiga
* pregled svih posudbi
* pregled aktivnih i vraćenih posudbi
* povrat knjige
* automatska promjena dostupnosti knjige

### Statistika i grafovi

* ukupan broj knjiga
* broj dostupnih i posuđenih knjiga
* broj aktivnih posudbi
* najposuđivanija knjiga
* najpopularnija kategorija
* prosječan broj posudbi po knjizi
* grafovi statistike korištenjem Chart.js biblioteke

## Tehnologije

* Java 21
* Spring Boot 4
* Spring Data JPA
* Hibernate
* Thymeleaf
* MySQL
* Bootstrap 5
* Chart.js
* Maven

## Baza podataka

Potrebno je kreirati MySQL bazu podataka:

```sql
CREATE DATABASE booktrack;
```

U datoteci `application.properties` potrebno je postaviti vlastite podatke za pristup bazi.

## Pokretanje aplikacije

1. Klonirati projekt
2. Kreirati MySQL bazu podataka `booktrack`
3. Podesiti korisničko ime i lozinku baze u `application.properties`
4. Pokrenuti projekt naredbom:

```bash
mvn spring-boot:run
```

ili pokretanjem klase:

```text
BooktrackApplication.java
```

5. Otvoriti preglednik i pristupiti adresi:

```text
http://localhost:8080
```

## Autor

Bruno Rebić

Projekt izrađen za kolegij Programiranje na Java virtualnom stroju, Fakultet informatike u Puli, 2026.
