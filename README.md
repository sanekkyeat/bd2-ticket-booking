# Sistem de Rezervare a Biletelor la Concert

Proiect realizat în cadrul disciplinei **Baze de Date Avansate (BD II)**.  
**SGBD utilizat:** PostgreSQL  
**Tehnologie backend propusă:** Java (Spring Boot)

---

## 1. Descrierea domeniului și a problemei
Aplicația oferă o soluție completă pentru gestionarea și rezervarea biletelor la evenimente muzicale și concerte. 
Sistemul permite organizatorilor să publice evenimente cu diferite categorii de bilete, iar clienților să rezerve și să achiziționeze locuri în timp real.

Alegerea **PostgreSQL** garantează integritatea tranzacțională (ACID) necesară procesării plăților și rezervărilor concurente, prevenind scenariile de tip *overbooking* (vânzarea aceluiași loc mai multor utilizatori).

---

## 2. Rolurile utilizatorilor în sistem
Sistemul definește 3 roluri distincte:
1. **Client (Cumpărător):**
    * Căutare și filtrare evenimente (după artist, locație, dată, gen).
    * Rezervare de bilete pe categorii de locuri.
    * Vizualizarea istoricului comenzilor și descărcarea biletelor în format PDF.
2. **Organizator:**
    * Creare, modificare și anulare evenimente proprii.
    * Alocare cote și prețuri pe categorii de bilete (General Access, VIP, Fan Zone).
    * Monitorizarea vânzărilor și generarea rapoartelor financiare per concert.
3. **Administrator:**
    * Administrarea conturilor de utilizatori și blocarea/activarea acestora.
    * Gestionarea nomenclatorului de săli și locații (Venues).
    * Acces la statistici globale ale platformei.

---

## 3. Entitățile principale identificate (pentru modelul de date)
* **Utilizatori (`users`)** — date de cont, credențiale (hash parolă), rol asociat.
* **Locații / Săli (`venues`)** — nume sală, oraș, adresă, capacitate maximă.
* **Evenimente (`events`)** — titlu, descriere, dată/oră, legături către sală și organizator.
* **Categorii de bilete (`ticket_categories`)** — tip bilet (ex. VIP, Standard), preț unitar, stoc total și disponibil.
* **Comenzi (`bookings`)** — utilizator cumpărător, data rezervării, statusul comenzii, total de plată.
* **Bilete emise (`tickets`)** — cod unic bilet (UUID/QR), categoria de bilet, ID-ul comenzii, status utilizare la intrare.

## 4. Structura bazei de date (Etapa 1)
Schema conține 6 tabele relaționale normalizate:
- `users`: Gestionarea conturilor și rolurilor (`CLIENT`, `ORGANIZER`, `ADMIN`).
- `venues`: Săli și arene (ex. Arena Națională, BTarena) cu validare pe capacitate pozitivă.
- `events`: Evenimentele programate, legate de organizator și locație.
- `ticket_categories`: Zone de preț per concert, cu constrângere de integritate pe stoc.
- `bookings`: Tranzacțiile și comenzile efectuate de utilizatori.
- `tickets`: Bilete emise individual cu cod unic UUID de acces și status de validare.

### Rularea scripturilor SQL:
1. Crearea tabelelor: `psql -d ticket_booking_db -f sql/schema.sql` (sau executare în pgAdmin 4).
2. Încărcarea datelor de test: `psql -d ticket_booking_db -f sql/fixtures.sql`.
## 5. Descrierea Paginilor Aplicației (Etapa 2 - UI / UX)

Sistemul include următoarele pagini web principale pentru interacțiunea cu utilizatorii:

### 1. Pagina Principală / Catalog Evenimente (`/events` sau `/`)
* **Destinație:** Accesibilă tuturor utilizatorilor (clienți neautentificați și autentificați).
* **Conținut & Elemente:**
    * Bară de căutare și filtre avansate după gen muzical, artist, oraș și dată.
    * Carduri de evenimente cu titlu, artist, locație (sală), dată/oră și prețul de pornire.
* **Operații CRUD invocate:**
    * **Read:** `GET /api/events`, `GET /api/venues`.

### 2. Pagina Detalii Eveniment & Selectare Bilete (`/events/{id}`)
* **Destinație:** Vizualizarea detaliilor concertului și configurarea comenzii.
* **Conținut & Elemente:**
    * Informații detaliate despre artist, sală și organizator.
    * Tabelul categoriilor de bilete disponibile (ex. VIP, Gazon, Peluză), prețul per loc și stocul curent (`available_quantity`).
    * Selector cantitate bilete și buton de adăugare în coș / rezervare.
* **Operații CRUD invocate:**
    * **Read:** `GET /api/events/{id}`, `GET /api/categories?eventId={id}`.

### 3. Pagina de Finalizare Rezervare & Checkout (`/checkout`)
* **Destinație:** Rezervată clienților autentificați pentru confirmarea achiziției.
* **Conținut & Elemente:**
    * Sumarul comenzii (categoriile alese, numărul de bilete, total de plată calculat).
    * Formular date de facturare/contact.
    * Buton de plată/confirmare rezervare.
* **Operații CRUD invocate:**
    * **Create:** `POST /api/bookings` (creare comandă cu generare automată de coduri în `/api/tickets`).
    * **Update:** `PUT /api/categories/{id}` (decrementarea biletelor rămase).

### 4. Panoul Clientului / Biletele Mele (`/profile/tickets`)
* **Destinație:** Istoricul achizițiilor pentru client.
* **Conținut & Elemente:**
    * Lista rezervărilor efectuate (`CONFIRMED`, `CANCELLED`).
    * Codurile unice UUID / QR pentru fiecare bilet emis, cu opțiunea de descărcare.
    * Buton de anulare a comenzii înainte de data concertului.
* **Operații CRUD invocate:**
    * **Read:** `GET /api/bookings?userId={id}`, `GET /api/tickets?bookingId={id}`.
    * **Update:** `PUT /api/bookings/{id}/cancel`.

### 5. Panoul Organizatorului / Gestionare Evenimente (`/organizer/dashboard`)
* **Destinație:** Crearea și administrarea concertelor proprii.
* **Conținut & Elemente:**
    * Formular de creare concert nou (titlu, artist, gen, dată, selectare sală).
    * Gestionare cote bilete (adăugare categorii tarifare și capacități).
    * Editare stare concert (`SCHEDULED`, `CANCELLED`, `COMPLETED`).
* **Operații CRUD invocate:**
    * **Create:** `POST /api/events`, `POST /api/categories`.
    * **Update:** `PUT /api/events/{id}`, `PUT /api/categories/{id}`.
    * **Delete:** `DELETE /api/events/{id}`.

### 6. Panoul de Validare Bilete la Intrare (`/gate/scanner`)
* **Destinație:** Personalul de acces la porțile sălii/stadionului.
* **Conținut & Elemente:**
    * Scaner cod UUID/bilet.
    * Afișare status validitate (bilet valid / deja utilizat / anulat).
* **Operații CRUD invocate:**
    * **Read:** `GET /api/tickets/verify/{ticketCode}`.
    * **Update:** `PUT /api/tickets/validate/{ticketCode}` (marcare `is_used = true`).