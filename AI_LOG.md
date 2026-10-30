# Jurnal de Utilizare a Asistenților AI (AI Log)

## Etapa 0: Inițializare proiect și stabilirea domeniului
* **Data:** 19 Octombrie 2026
* **Asistent utilizat:** Gemini
* **Prompt principal:** Brainstorming structură domeniu «Rezervarea biletelor la concert», identificare entități și mapare pe cerințele disciplinei BD II.
* **Rezultat propus de AI:** Schemă generică de e-commerce cu produse și stocuri simple.
* **Erori / Neconcordanțe identificate:** Modelul inițial nu ținea cont de specificul concertelor (locații fizice, capacitate pe sală, tipuri de bilete per eveniment) și nu evidenția clar cele 3 roluri cerute în baremul etapei 3.
* **Corecție aplicată de student:** Structura a fost adaptată la un sistem de ticketing dedicat, cu entități specifice (`venues`, `events`, `ticket_categories`, `bookings`, `tickets`) și delimitarea rolurilor (Client, Organizator, Admin).

## Etapa 1: Proiectarea schemei relaționale și crearea de fixtures
* **Data:** 19 Octombrie 2026
* **Asistent utilizat:** Gemini
* **Prompt principal:** Generare DDL pentru 6 tabele relaționale în PostgreSQL cu chei străine, constrângeri `CHECK` și crearea unui set inițial de date de test (fixtures) pentru concertul Iron Maiden la Arena Națională.
* **Rezultat propus de AI:** Schemă inițială ce combina `bookings` și `tickets` într-un singur tabel și folosea locații generice de teatru.
* **Erori / Neconcordanțe identificate:** Lipsa separării comenzii de biletul efectiv făcea imposibilă emiterea de coduri unice QR/UUID per loc în cazul comenzilor multiple. De asemenea, datele de test inițiale aveau capacități nerealiste pentru concerte rock majore.
* **Corecție aplicată de student:** A fost introdus tabelul separat `tickets` cu constrângere `UNIQUE` pe codul de acces și tabelul `ticket_categories` cu constrângere de verificare `available_quantity <= total_quantity`. Locația principală a fost setată pe Arena Națională (55.600 locuri), adaptând categoriile tarifare corespunzător.

## Etapa 2: Implementare operații CRUD și descrierea paginilor
* **Data:** 30 Octombrie 2026
* **Asistent utilizat:** Gemini
* **Prompt principal:** Configurare Spring Boot 3.3.5, depanare erori de compilare Maven/JDK, generare entități JPA, interfețe de repository și controlere REST pentru operațiile CRUD.
* **Rezultat propus de AI:** Dependențe generate inițial pentru versiuni incompatibile de starter-e și maparea implicită a cheilor `SERIAL` la tipul `Long`.
* **Erori / Neconcordanțe identificate:**
    1. Eroare Maven Plexus/Guice (`ArtifactHandlerManager missing`) din cauza incompatibilității versiunii interne de Java din IDE.
    2. Eroare Hibernate SchemaManagementException (`wrong column type: found serial/integer, expecting bigint`) pe coloana `id` din `venues` și `events`.
    3. Eroare lipsă coloană `description` în tabela `events` (schema conținea `artist` și `genre`).
* **Corecție aplicată de student:** Mediul de import Maven a fost setat pe JDK 21 (Temurin), câmpurile de cheie primară au fost corectate la `Integer`, iar entitatea `Event` a fost aliniată cu definițiile `artist` și `genre` din `schema.sql`. Toate endpoint-urile CRUD au fost verificate cu succes prin răspunsuri JSON valide.