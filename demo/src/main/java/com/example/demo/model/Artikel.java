package com.example.demo.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Artikel
 *
 * Dieses Entity beschreibt einen einzelnen Artikel in einer Einkaufsliste.
 * Jeder Artikel gehört genau zu einem Benutzer.
 *
 * Ein Artikel ist ein Datensatz in der Tabelle "artikel".
 */
@Entity // JPA-Entity → wird als Tabelle in der Datenbank gespeichert
@Table(name = "artikel") // Tabellenname in der Datenbank
@Getter // Lombok erzeugt automatisch Getter
@Setter // Lombok erzeugt automatisch Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// JPA benötigt einen parameterlosen Konstruktor
@AllArgsConstructor // Konstruktor mit allen Attributen
@Builder // Ermöglicht das Builder-Pattern
public class Artikel {

    /**
     * Primärschlüssel:
     * Wird von der Datenbank automatisch vergeben (Auto-Increment).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Besitzer des Artikels.
     *
     * ownerId verweist logisch auf AppUser.id.
     * (Hier bewusst OHNE @ManyToOne, um es für Schüler einfacher zu halten.)
     */
    @Column(nullable = false)
    private Long ownerId;

    /**
     * Name des Artikels (z.B. "Apfel", "Milch").
     */
    @Column(nullable = false)
    private String name;

    /**
     * Menge des Artikels (z.B. 3 Stück).
     */
    @Column(nullable = false)
    private int menge;

    /**
     * Preis pro Stück.
     *
     * precision = 10 → insgesamt maximal 10 Stellen
     * scale = 2 → davon 2 Nachkommastellen
     *
     * BigDecimal wird für Geldbeträge genutzt,
     * da double zu Rundungsfehlern führen kann.
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preis;

    /**
     * Gesamtpreis des Artikels:
     * Menge × Einzelpreis
     *
     * Diese Methode wird NICHT in der Datenbank gespeichert,
     * sondern jedes Mal zur Laufzeit berechnet.
     */
    public BigDecimal getGesamtpreis() {
        return preis.multiply(BigDecimal.valueOf(menge));
    }
}
