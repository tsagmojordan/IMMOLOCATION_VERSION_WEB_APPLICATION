package com.example.immolocation.Service;


import com.example.immolocation.Model.Bailleur;
import com.example.immolocation.Model.Locataire;
import com.example.immolocation.Model.Proprietes;

import java.util.List;

public interface ILocataireServices {

    //*********ajoute un locataire
    String addLocataire(Locataire locataire, Bailleur bailleur, Proprietes propriete);

    //*********supprime un locataire
    void deleteLocatire(Locataire locataire);

    List<Locataire> findAllByBailleur(Bailleur bailleur);

    //**********rechercher un locataire a travers la propriete qu'il occupe
    Locataire rechercherParPropriete(Proprietes propriete);

    //*********rrehercher locataire par son login
    public Locataire rechercherLocataire(String login);


    void modifierLocataire(Locataire locataire);

    Locataire rechercherParId(String id);
}
