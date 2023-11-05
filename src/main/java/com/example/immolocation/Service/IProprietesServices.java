package com.example.immolocation.Service;

import com.example.immolocation.Model.Bailleur;
import com.example.immolocation.Model.Proprietes;

import java.util.List;
import java.util.Optional;

public interface IProprietesServices {
    public Bailleur retourneBailleur(Proprietes propriete);

    public List<Proprietes> proprieteLibreParBailleur(Bailleur bailleur);
    public List<Proprietes> findAllFreePropriete() ;
    public void ajouterProprieter(Proprietes propriete,Bailleur bailleur) ;

    //********************la suppression d'une propriete occupé entraine la suppression de son locataire******************************
    public void supprimerPropriete(Long id) ;


    public void modifierPropriete(Long id,Proprietes propriete);




    public Proprietes consulterPropriete(Long id) ;
    public Proprietes setDisponibilite(String etat, Proprietes propriete);
    public List<Proprietes> findByLocalisation(String localisation);

    public List<Proprietes> findByPrix(Long prixiInf, Long prixSup);
    public List<Proprietes> listProprieteparBailleur(Bailleur bailleur);
    public Proprietes findByName(String name,Bailleur bailleur);


    public Optional<Proprietes> consulterProp(Long id);



    public void savePropriete(Proprietes propriete);


    public Optional<Proprietes> getProprieteById(Long id);

    public void supprimerProprieteOccupe(Long id);


    public List<Proprietes> getAllActivePropriete();




}
