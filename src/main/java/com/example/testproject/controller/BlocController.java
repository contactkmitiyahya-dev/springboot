package com.example.testproject.controller;

import com.example.testproject.entities.Bloc;
import com.example.testproject.services.IBlocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bloc")
public class BlocController {

    @Autowired
    private IBlocService blocService;

    @PostMapping
    public Bloc addBloc(@RequestBody Bloc bloc) {
        return blocService.addBloc(bloc);
    }

    @GetMapping("/{id}")
    public Bloc getBlocById(@PathVariable long id) {
        return blocService.getBlocById(id);
    }

    @PutMapping("/{id}")
    public Bloc updateBloc(@PathVariable long id, @RequestBody Bloc bloc) {
        bloc.setIdBloc(id);
        return blocService.updateBloc(bloc);
    }

    @DeleteMapping("/{id}")
    public void deleteBloc(@PathVariable long id) {
        blocService.deleteBloc(id);
    }

    @GetMapping
    public List<Bloc> getAllBlocs() {
        return blocService.getAllBlocs();
    }

    @PutMapping("/{idBloc}/affecter-chambres")
    public Bloc affecterChambresABloc(@PathVariable long idBloc, @RequestBody List<Long> numChambre) {
        return blocService.affecterChambresABloc(numChambre, idBloc);
    }
}