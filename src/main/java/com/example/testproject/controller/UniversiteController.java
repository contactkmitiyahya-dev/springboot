package com.example.testproject.controller;

import com.example.testproject.entities.Foyer;
import com.example.testproject.entities.Universite;
import com.example.testproject.services.IUniversiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/universite")
public class UniversiteController {

    @Autowired
    private IUniversiteService universiteService;

    @PostMapping
    public Universite addUniversite(@RequestBody Universite universite) {
        return universiteService.addUniversite(universite);
    }

    @GetMapping("/{id}")
    public Universite getUniversiteById(@PathVariable Long id) {
        return universiteService.getUniversiteById(id);
    }

    @PutMapping("/{id}")
    public Universite updateUniversite(@PathVariable Long id, @RequestBody Universite universite) {
        universite.setIdUniversite(id);
        return universiteService.updateUniversite(universite);
    }

    @DeleteMapping("/{id}")
    public void deleteUniversite(@PathVariable Long id) {
        universiteService.deleteUniversite(id);
    }

    @GetMapping
    public List<Universite> getAllUniversites() {
        return universiteService.getAllUniversites();
    }

    @PutMapping("/{id}/affecter-foyer")
    public Universite affecterFoyerToUniversite(@PathVariable Long id, @RequestBody Foyer foyer) {
        return universiteService.affecterFoyerToUniversite(id, foyer);
    }

    @PutMapping("/affecter-foyer")
    public Universite affecterFoyerToUniversite(@RequestBody Universite universite) {
        return universiteService.affecterFoyerToUniversite(universite);
    }

    @PutMapping("/{idFoyer}/affecter/{nomUniversite}")
    public Universite affecterFoyerAUniversite(@PathVariable long idFoyer, @PathVariable String nomUniversite) {
        return universiteService.affecterFoyerAUniversite(idFoyer, nomUniversite);
    }

    @PutMapping("/{idUniversite}/desaffecter-foyer")
    public Universite desaffecterFoyerAUniversite(@PathVariable long idUniversite) {
        return universiteService.desaffecterFoyerAUniversite(idUniversite);
    }
}