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