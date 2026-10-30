# Architectural Decision Records (ADR)

## ADR 01: Alegerea SGBD-ului și a temei proiectului
* **Data:** 19 Octombrie 2026
* **Status:** Acceptat
* **Context:** Pentru disciplina Baze de Date Avansate este necesară dezvoltarea unei aplicații bazate pe un singur SGBD relațional, cu suport pentru tranzacții, concurență, indexare măsurabilă și securitate pe roluri.
* **Decizie:** S-a ales tema **„Rezervarea biletelor la concert”** implementată pe **PostgreSQL**.
* **Justificare:**
    1. PostgreSQL gestionează excelent izolarea tranzacțiilor și concurența (MVCC), esențiale pentru prevenirea cumpărării aceluiași bilet de către doi clienți simultan.
    2. Oferă instrumente native avansate de analiză a planurilor de execuție (`EXPLAIN ANALYZE`) pentru justificarea indecșilor.
    3. PostgreSQL este un standard industrial robust pentru sisteme tranzacționale complexe.

## ADR 02: Structura modelului de date și constrângeri de integritate
* **Data:** 19 Octombrie 2026
* **Status:** Acceptat
* **Context:** Pentru Etapa 1 este necesară proiectarea unei scheme relaționale cu cel puțin 6 tabele, respectând formele normale și asigurând integritatea biletelor și a capacităților de sală.
* **Decizie:** S-au definit 6 tabele principale: `users`, `venues`, `events`, `ticket_categories`, `bookings`, `tickets`.
* **Justificare:**
    1. **Separarea `events` de `ticket_categories`:** Permite definirea mai multor zone tarifare pentru același concert (ex. VIP, Gazon, Tribuna) cu monitorizarea independentă a disponibilității (`available_quantity <= total_quantity`).
    2. **Separarea `bookings` de `tickets`:** O comandă poate conține mai multe bilete individuale. Fiecare bilet are un `ticket_code` unic (UUID) pentru validarea accesului la intrare (`is_used`).
    3. **Integritate referențială:** Utilizarea `ON DELETE RESTRICT` pentru locații și organizatori pentru a preveni ștergerea accidentală a istoricului evenimentelor, respectiv `ON DELETE CASCADE` pentru biletele asociate unei comenzi anulate.
    4. **Tipuri de date native PostgreSQL:** Folosirea `TIMESTAMPTZ` pentru manipularea sigură a fusurilor orare ale concertelor și `NUMERIC(10, 2)` pentru evitarea erorilor de rotunjire financiară.

## ADR 03: Arhitectura REST API și izolarea pe straturi (Layered Architecture)
* **Data:** 30 Octombrie 2026
* **Status:** Acceptat
* **Context:** Implementarea cerințelor din Etapa 2 (CRUD) folosind Spring Boot 3 și PostgreSQL, asigurând decuplarea responsabilităților și conformitatea cu schema relațională.
* **Decizie:** S-a adoptat arhitectura standard pe 4 straturi:
    1. `entity`: Maparea tabelelor din schema relațională (`venues`, `events`, `ticket_categories`, `bookings`, `tickets`, `users`). Cheile primare auto-incrementate `SERIAL` au fost mapate strict la tipul Java `Integer` pentru consistență cu PostgreSQL.
    2. `repository`: Utilizarea interfețelor `JpaRepository` pentru generarea automată a instrucțiunilor SQL de citire, filtrare și manipulare a datelor.
    3. `controller`: Expunerea punctelor de acces RESTful (`/api/venues`, `/api/events`, `/api/categories`, `/api/bookings`, `/api/tickets`) cu coduri HTTP standard (`200 OK`, `201 CREATED`, `404 NOT FOUND`, `204 NO CONTENT`).
* **Justificare:** Structura oferă o mentenanță simplă, suport facil pentru adăugarea stratului de securitate (Etapa 3) și teste automate.