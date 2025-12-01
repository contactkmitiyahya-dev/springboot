package com.example.testproject.services;

import com.example.testproject.entities.Bloc;
import com.example.testproject.entities.Chambre;
import com.example.testproject.repository.BlocRepository;
import com.example.testproject.repository.ChambreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BlocServiceImpl implements IBlocService {

    @Autowired
    private BlocRepository blocRepository;

    @Autowired
    private ChambreRepository chambreRepository;

    @Override
    public Bloc addBloc(Bloc bloc) {
        return blocRepository.save(bloc);
    }

    @Override
    public List<Bloc> getAllBlocs() {
        return blocRepository.findAll();
    }

    @Override
    public Bloc getBlocById(long id) {
        return blocRepository.findById(id).orElse(null);
    }

    @Override
    public Bloc updateBloc(Bloc bloc) {
        return blocRepository.save(bloc);
    }

    @Override
    public void deleteBloc(long id) {
        blocRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Bloc affecterChambresABloc(List<Long> numChambre, long idBloc) {
        Bloc bloc = blocRepository.findById(idBloc).orElse(null);

        if (bloc == null) {
            return null;
        }

        List<Chambre> chambres = chambreRepository.findByNumeroChambreIn(numChambre);

        if (chambres.isEmpty()) {
            return null;
        }

        for (Chambre chambre : chambres) {
            chambre.setBloc(bloc);
        }

        chambreRepository.saveAll(chambres);
        return blocRepository.findById(idBloc).orElse(null);
    }
}