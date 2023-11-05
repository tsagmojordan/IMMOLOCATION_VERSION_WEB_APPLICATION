package com.example.immolocation.Service;


import com.example.immolocation.Dao.ProprietesRepository;
import com.example.immolocation.Model.Proprietes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LouerServiceImpl implements ILouerServices{

    @Autowired
    ProprietesRepository proprietesRepository;
    @Override
    public void ajouterPropriete(Proprietes propriete) {
        proprietesRepository.save(propriete);
    }

    @Override
    public void arracher() {

    }


}
