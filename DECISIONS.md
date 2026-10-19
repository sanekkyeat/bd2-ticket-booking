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