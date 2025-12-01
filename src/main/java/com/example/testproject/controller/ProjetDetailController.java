package com.example.testproject.controller;

import com.example.testproject.DTO.ProjetDetailDTO;
import com.example.testproject.entities.ProjetDetail;
import com.example.testproject.services.ProjetDetailDTOService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/DTO")
@RequiredArgsConstructor
public class ProjetDetailController {

    private final ProjetDetailDTOService projetDetailDTOService;

    // Afficher les détails d'un projet par ID
    @GetMapping("/{id}/details")
    public ProjetDetailDTO getDetails(@PathVariable long id) {
        return projetDetailDTOService.getDetailsProjet(id);
    }

    @GetMapping("/all")
    public List<ProjetDetailDTO> getAllProjects() {
        return projetDetailDTOService.getAllProjects();
    }

    @PostMapping("/add")
    public ProjetDetailDTO addProject(@RequestBody ProjetDetailDTO projetDetailDTO) {
        return projetDetailDTOService.addProject(projetDetailDTO);
    }

    @GetMapping("/test")
    public String test() {
        return "API ProjetDetail is working!";
    }
}