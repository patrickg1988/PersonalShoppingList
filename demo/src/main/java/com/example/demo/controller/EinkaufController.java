package com.example.demo.controller;

import java.math.BigDecimal;
import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.service.EinkaufService;

/**
 * EinkaufController
 *
 * Dieser Controller kümmert sich um alles rund um die Einkaufsliste:
 * - Anzeigen der Liste
 * - Hinzufügen von Artikeln
 * - Löschen von Artikeln
 *
 * Er ist Teil der MVC-Struktur:
 * - Controller: verarbeitet Anfragen
 * - Service: enthält die Geschäftslogik
 * - View: stellt die Daten dar (Thymeleaf-Templates)
 */
@Controller
public class EinkaufController {

    /**
     * Service für die Einkaufslogik.
     * Dort wird z.B. entschieden:
     * - Welche Artikel gehören zu welchem Benutzer?
     * - Wie wird der Gesamtpreis berechnet?
     */
    private final EinkaufService einkaufService;

    /**
     * Constructor Injection:
     * Spring übergibt automatisch ein EinkaufService-Objekt.
     */
    public EinkaufController(EinkaufService einkaufService) {
        this.einkaufService = einkaufService;
    }

    /**
     * GET /einkaufsliste
     *
     * Zeigt die Einkaufsliste des aktuell eingeloggten Benutzers an.
     *
     * Model:
     * - Übergibt Daten an das HTML-Template
     *
     * Principal:
     * - Enthält Informationen über den eingeloggten Benutzer
     * - principal.getName() liefert den Benutzernamen
     */
    @GetMapping("/einkaufsliste")
    public String einkaufsliste(Model model, Principal principal) {

        // Benutzername des eingeloggten Users (z.B. aus Spring Security)
        String username = principal.getName();

        // Liste aller Artikel dieses Benutzers an die View übergeben
        model.addAttribute("artikelListe", einkaufService.findAllForUser(username));

        // Gesamtpreis der Einkaufsliste berechnen und an die View übergeben
        model.addAttribute("gesamtPreis", einkaufService.gesamtPreis(username));

        // Gibt das Template templates/einkaufsliste.html zurück
        return "einkaufsliste";
    }

    /**
     * POST /artikel
     *
     * Fügt einen neuen Artikel zur Einkaufsliste hinzu.
     *
     * @RequestParam:
     * - Liest die Werte aus dem Formular aus
     *   (name="name", name="menge", name="preis")
     *
     * BigDecimal:
     * - Wird für Geldbeträge verwendet, da es genauer ist als double
     */
    @PostMapping("/artikel")
    public String addArtikel(@RequestParam String name,
                             @RequestParam int menge,
                             @RequestParam BigDecimal preis,
                             Principal principal) {

        // Artikel für den aktuellen Benutzer speichern
        einkaufService.addArtikel(
                principal.getName(), // Benutzername
                name,                // Artikelname
                menge,               // Menge
                preis                // Einzelpreis
        );

        // Nach dem Absenden weiterleiten zur Einkaufsliste (PRG-Pattern)
        return "redirect:/einkaufsliste";
    }

    /**
     * POST /artikel/loeschen
     *
     * Löscht einen Artikel anhand seiner ID.
     *
     * Die ID kommt z.B. aus einem versteckten Formularfeld oder Button.
     */
    @PostMapping("/artikel/loeschen")
    public String deleteArtikel(@RequestParam Long id, Principal principal) {

        // Löscht den Artikel nur für den aktuell eingeloggten Benutzer
        einkaufService.deleteArtikel(principal.getName(), id);

        // Danach wieder zurück zur Einkaufsliste
        return "redirect:/einkaufsliste";
    }
}
