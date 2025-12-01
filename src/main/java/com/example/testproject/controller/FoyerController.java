package com.example.testproject.controller;

import com.example.testproject.entities.Foyer;
import com.example.testproject.services.IFoyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/foyer")
public class FoyerController {

    @Autowired
    private IFoyerService foyerService;

    @PostMapping
    public Foyer addFoyer(@RequestBody Foyer foyer) {
        return foyerService.addFoyer(foyer);
    }

    @GetMapping("/{id}")
    public Foyer getFoyerById(@PathVariable long id) {
        return foyerService.getFoyerById(id);
    }

    @PutMapping("/{id}")
    public Foyer updateFoyer(@PathVariable long id, @RequestBody Foyer foyer) {
        foyer.setIdFoyer(id);
        return foyerService.updateFoyer(foyer);
    }

    @DeleteMapping("/{id}")
    public void deleteFoyer(@PathVariable long id) {
        foyerService.deleteFoyer(id);
    }

    @GetMapping
    public List<Foyer> getAllFoyers() {
        return foyerService.getAllFoyers();
    }

    @PostMapping("/ajouter-et-affecter/{idUniversite}")
    public Foyer ajouterFoyerEtAffecterAUniversite(@RequestBody Foyer foyer, @PathVariable long idUniversite) {
        return foyerService.ajouterFoyerEtAffecterAUniversite(foyer, idUniversite);
    }
}