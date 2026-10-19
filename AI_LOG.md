# Jurnal de Utilizare a Asistenților AI (AI Log)

## Etapa 0: Inițializare proiect și stabilirea domeniului
* **Data:** 19 Octombrie 2026
* **Asistent utilizat:** Gemini
* **Prompt principal:** Brainstorming structură domeniu «Rezervarea biletelor la concert», identificare entități și mapare pe cerințele disciplinei BD II.
* **Rezultat propus de AI:** Schemă generică de e-commerce cu produse și stocuri simple.
* **Erori / Neconcordanțe identificate:** Modelul inițial nu ținea cont de specificul concertelor (locații fizice, capacitate pe sală, tipuri de bilete per eveniment) și nu evidenția clar cele 3 roluri cerute în baremul etapei 3.
* **Corecție aplicată de student:** Structura a fost adaptată la un sistem de ticketing dedicat, cu entități specifice (`venues`, `events`, `ticket_categories`, `bookings`, `tickets`) și delimitarea rolurilor (Client, Organizator, Admin).