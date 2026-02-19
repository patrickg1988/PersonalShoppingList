package com.example.demo.model;

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
 * AppUser
 *
 * Diese Klasse ist ein JPA-Entity und repräsentiert einen Benutzer
 * in der Datenbank (Tabelle: "users").
 *
 * 
 * Ein Entity = eine Java-Klasse, die direkt einer Tabelle in der Datenbank entspricht.
 */
@Entity // Markiert die Klasse als JPA-Entity
@Table(name = "users") // Verknüpft die Klasse mit der Tabelle "users"
@Getter // Lombok: erzeugt automatisch Getter-Methoden für alle Attribute
@Setter // Lombok: erzeugt automatisch Setter-Methoden für alle Attribute
@NoArgsConstructor(access = AccessLevel.PROTECTED) 
// JPA benötigt immer einen parameterlosen Konstruktor.
// PROTECTED verhindert, dass man ihn "aus Versehen" im Code nutzt.
@AllArgsConstructor // Lombok: erzeugt einen Konstruktor mit allen Attributen
@Builder // Lombok: ermöglicht die Erstellung von Objekten mit dem Builder-Pattern
public class AppUser {

    /**
     * Primärschlüssel der Tabelle.
     * Wird von der Datenbank automatisch erzeugt.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // IDENTITY = Auto-Increment (z.B. 1, 2, 3, ...)
    private Long id;

    /**
     * Benutzername:
     * - darf nicht leer sein
     * - muss eindeutig sein (kein zweiter Benutzer mit gleichem Namen)
     */
    @Column(nullable = false, unique = true)
    private String username;

    /**
     * Passwort-Hash:
     * - hier wird NICHT das Klartext-Passwort gespeichert!
     * - stattdessen ein Hash (z.B. BCrypt)
     *
     * Wichtig für Sicherheit:
     * Niemals Passwörter im Klartext in der Datenbank speichern.
     */
    @Column(nullable = false)
    private String passwordHash;

    /**
     * Rolle des Benutzers (z.B. USER, ADMIN).
     *
     * @Builder.Default:
     * - sorgt dafür, dass beim Builder automatisch "USER" gesetzt wird,
     *   wenn keine Rolle angegeben wird.
     */
    @Builder.Default
    @Column(nullable = false)
    private String role = "USER";

}
