package com.example.testproject.services;

import com.example.testproject.entities.Bloc;
import java.util.List;

public interface IBlocService {
    Bloc addBloc(Bloc bloc);
    List<Bloc> getAllBlocs();
    Bloc getBlocById(long id);
    Bloc updateBloc(Bloc bloc);
    void deleteBloc(long id);
    Bloc affecterChambresABloc(List<Long> numChambre, long idBloc);
}