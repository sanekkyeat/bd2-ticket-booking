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