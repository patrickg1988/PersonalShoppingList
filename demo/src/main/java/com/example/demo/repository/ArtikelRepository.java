package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Artikel;

/**
 * ArtikelRepository
 *
 * Dieses Interface ist ein Spring-Data-JPA-Repository.
 *
 * Hier wird KEIN Code geschrieben – und trotzdem funktioniert alles!
 *
 * Spring Data JPA erzeugt die Implementierung automatisch zur Laufzeit.
 */
public interface ArtikelRepository extends JpaRepository<Artikel, Long> {

    /**
     * findByOwnerIdOrderByIdDesc(...)
     *
     * Diese Methode existiert NUR durch ihren Namen.
     * Spring Data JPA "liest" den Methodennamen und baut automatisch
     * die passende SQL-Abfrage.
     *
     * Bedeutung des Namens:
     *
     * findBy           → Suche Datensätze
     * OwnerId          → WHERE owner_id = ?
     * OrderByIdDesc    → ORDER BY id DESC
     *
     * Entspricht ungefähr folgendem SQL:
     *
     * SELECT * 
     * FROM artikel
     * WHERE owner_id = ?
     * ORDER BY id DESC;
     *
     * Rückgabe:
     * - List<Artikel> → alle passenden Artikel
     */
    List<Artikel> findByOwnerIdOrderByIdDesc(Long ownerId);

    /**
     * deleteByIdAndOwnerId(...)
     *
     * Löscht einen Artikel nur dann,
     * wenn BOTH Bedingungen erfüllt sind:
     * - id stimmt
     * - ownerId stimmt
     *
     * Bedeutung des Namens:
     *
     * deleteBy         → Löschen
     * Id               → WHERE id = ?
     * AndOwnerId       → AND owner_id = ?
     *
     * Entspricht ungefähr folgendem SQL:
     *
     * DELETE FROM artikel
     * WHERE id = ?
     * AND owner_id = ?;
     *
     * 
     */
    void deleteByIdAndOwnerId(Long id, Long ownerId);
}
