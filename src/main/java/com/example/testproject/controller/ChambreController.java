package com.example.testproject.controller;

import com.example.testproject.entities.Chambre;
import com.example.testproject.entities.TypeChambre;
import com.example.testproject.services.IChambreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chambre")
public class ChambreController {

    @Autowired
    private IChambreService chambreService;

    @PostMapping
    public Chambre addChambre(@RequestBody Chambre chambre) {
        return chambreService.addChambre(chambre);
    }

    @GetMapping("/{id}")
    public Chambre getChambreById(@PathVariable long id) {
        return chambreService.getChambreById(id);
    }

    @PutMapping("/{id}")
    public Chambre updateChambre(@PathVariable long id, @RequestBody Chambre chambre) {
        chambre.setIdChambre(id);
        return chambreService.updateChambre(chambre);
    }

    @DeleteMapping("/{id}")
    public void deleteChambre(@PathVariable long id) {
        chambreService.deleteChambre(id);
    }

    @GetMapping
    public List<Chambre> getAllChambres() {
        return chambreService.getAllChambres();
    }

    @GetMapping("/universite/{nomUniversite}")
    public List<Chambre> getChambresParNomUniversite(@PathVariable String nomUniversite) {
        return chambreService.getChambresParNomUniversite(nomUniversite);
    }

    @GetMapping("/bloc/{idBloc}/type/{typeC}")
    public List<Chambre> getChambresParBlocEtType(@PathVariable long idBloc, @PathVariable TypeChambre typeC) {
        return chambreService.getChambresParBlocEtType(idBloc, typeC);
    }
    @GetMapping("/non-reservees/universite/{nomUniversite}/type/{type}")
    public List<Chambre> getChambresNonReserveParNomUniversiteEtTypeChambre(
            @PathVariable String nomUniversite,
            @PathVariable TypeChambre type) {
        return chambreService.getChambresNonReserveParNomUniversiteEtTypeChambre(nomUniversite, type);
    }
}