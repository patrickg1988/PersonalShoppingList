package com.example.demo.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.AppUser;
import com.example.demo.model.Artikel;
import com.example.demo.repository.ArtikelRepository;
import com.example.demo.repository.UserRepository;

@Service
public class EinkaufService {

    private final ArtikelRepository artikelRepo;
    private final UserRepository userRepo;

    public EinkaufService(ArtikelRepository artikelRepo, UserRepository userRepo) {
        this.artikelRepo = artikelRepo;
        this.userRepo = userRepo;
    }

    private Long ownerIdForUsername(String username) {
        AppUser user = userRepo.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User nicht gefunden: " + username));
        return user.getId();
    }

    @Transactional
    public void addArtikel(String username, String name, int menge, BigDecimal preis) {
        Long ownerId = ownerIdForUsername(username);

        Artikel artikel = Artikel.builder()
                .ownerId(ownerId)
                .name(name)
                .menge(menge)
                .preis(preis)
                .build();

        artikelRepo.save(artikel);
    }

    @Transactional(readOnly = true)
    public List<Artikel> findAllForUser(String username) {
        Long ownerId = ownerIdForUsername(username);
        return artikelRepo.findByOwnerIdOrderByIdDesc(ownerId);
    }

    @Transactional
    public void deleteArtikel(String username, Long artikelId) {
        Long ownerId = ownerIdForUsername(username);
        artikelRepo.deleteByIdAndOwnerId(artikelId, ownerId);
    }

    @Transactional(readOnly = true)
    public BigDecimal gesamtPreis(String username) {
        return findAllForUser(username).stream()
                .map(Artikel::getGesamtpreis)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
