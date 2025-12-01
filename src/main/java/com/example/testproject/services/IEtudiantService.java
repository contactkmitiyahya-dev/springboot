package com.example.testproject.services;

import com.example.testproject.entities.Etudiant;
import java.util.List;

public interface IEtudiantService {
    Etudiant addEtudiant(Etudiant etudiant);
    List<Etudiant> getAllEtudiants();
    Etudiant getEtudiantById(long id);
    Etudiant updateEtudiant(Etudiant etudiant);
    void deleteEtudiant(long id);
}