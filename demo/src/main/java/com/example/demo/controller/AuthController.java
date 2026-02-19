package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.service.UserService;

/**
 * AuthController = Controller für alles rund um "Anmelden" und "Registrieren".
 *
 * Aufgabe eines Controllers (MVC):
 * - Nimmt HTTP-Anfragen entgegen (z.B. GET /login)
 * - Ruft bei Bedarf Logik im Service auf (z.B. userService.register(...))
 * - Gibt zurück, welche View (HTML/Thymeleaf-Template) angezeigt werden soll
 */
@Controller // Markiert diese Klasse als Spring MVC Controller (liefert Views zurück, kein JSON)
public class AuthController {

    /**
     * Service-Schicht:
     * Hier steckt die "Business-Logik" (z.B. Benutzer anlegen, prüfen, speichern).
     * Der Controller selbst soll möglichst "dünn" bleiben.
     */
    private final UserService userService;

    /**
     * Constructor Injection (empfohlen):
     * Spring erstellt das AuthController-Objekt und "gibt" automatisch ein UserService-Objekt hinein.
     * Vorteil: Felder können final sein, besser testbar, klarer Aufbau.
     */
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * GET /register
     * - Wird aufgerufen, wenn jemand die Registrierungsseite öffnen will.
     * - Rückgabewert ist der View-Name: "register" => templates/register.html
     */
    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    /**
     * POST /register
     * - Wird aufgerufen, wenn das Registrierungsformular abgeschickt wird.
     *
     * @RequestParam:
     * - Liest die Formulardaten aus dem Request (name="username", name="password") aus.
     *
     * Model:
     * - Damit kann man Daten an die View übergeben (z.B. Fehlermeldung).
     */
    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           Model model) {

        try {
            // trim() entfernt Leerzeichen am Anfang/Ende, damit " patrick " nicht als anderer Nutzer gilt
            // Der Service entscheidet, ob Username/Passwort gültig sind und speichert den neuen User.
            userService.register(username.trim(), password);

            // redirect: sorgt dafür, dass der Browser eine neue GET-Anfrage macht
            // /login?registered kann in der Login-Seite genutzt werden, um "Registrierung erfolgreich" anzuzeigen
            return "redirect:/login?registered";

        } catch (IllegalArgumentException ex) {
            // Wenn der Service absichtlich eine Exception wirft (z.B. Username schon vergeben / Passwort zu kurz),
            // fangen wir sie ab und zeigen die Registrierung erneut an.
            model.addAttribute("error", ex.getMessage()); // in register.html dann z.B. th:text="${error}"
            return "register";
        }
    }

    /**
     * GET /login
     * - Zeigt die Login-Seite an.
     * - Das eigentliche Einloggen übernimmt später typischerweise Spring Security (Filter),
     *   nicht dieser Controller.
     */
    @GetMapping("/login")
    public String login() {
        return "login"; // => templates/login.html
    }
}
