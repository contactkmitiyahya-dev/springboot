package com.example.testproject.controller;

import com.example.testproject.entities.Etudiant;
import com.example.testproject.services.IEtudiantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/etudiant")
public class EtudiantController {

    @Autowired
    private IEtudiantService etudiantService;

    @PostMapping
    public Etudiant addEtudiant(@RequestBody Etudiant etudiant) {
        return etudiantService.addEtudiant(etudiant);
    }

    @GetMapping("/{id}")
    public Etudiant getEtudiantById(@PathVariable long id) {
        return etudiantService.getEtudiantById(id);
    }

    @PutMapping("/{id}")
    public Etudiant updateEtudiant(@PathVariable long id, @RequestBody Etudiant etudiant) {
        etudiant.setIdEtudiant(id);
        return etudiantService.updateEtudiant(etudiant);
    }

    @DeleteMapping("/{id}")
    public void deleteEtudiant(@PathVariable long id) {
        etudiantService.deleteEtudiant(id);
    }

    @GetMapping
    public List<Etudiant> getAllEtudiants() {
        return etudiantService.getAllEtudiants();
    }
}