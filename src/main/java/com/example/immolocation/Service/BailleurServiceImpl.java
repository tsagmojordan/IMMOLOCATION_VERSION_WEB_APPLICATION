package com.example.immolocation.Service;

import com.example.immolocation.Dao.BailleurRepository;
import com.example.immolocation.Model.Bailleur;
import com.example.immolocation.Model.Locataire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BailleurServiceImpl implements IBailleurServices {

    @Autowired
    BailleurRepository bailleurRepository;



    @Override
    public Bailleur rechercherBailleur(String login) {
        return bailleurRepository.retoureBailleur(login);
    }


    @Override
    public void ajouterBailleur(Bailleur bailleur) {
        bailleurRepository.save(bailleur);
    }

    @Override
    public Optional<Bailleur> rechercherParId(Long id) {
        return bailleurRepository.findById(id);
    }

    public Bailleur rechercherBailleurParLogin(String login) {

        Bailleur bailleur = bailleurRepository.retoureBailleur(login);
        return bailleur;


    }
    public List<Locataire> locataireselonloginBailleur(String login) {
        List<Locataire> locataire = bailleurRepository.liste_loc_selon_Bailleurlogin(login);
        return locataire;
    }
}
