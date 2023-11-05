package com.example.immolocation.Service;

import com.example.immolocation.Model.User;

import java.util.List;

public interface IUserServices {

//cette methode génère des caractères
// utilisés pour le password locataire

    String GenerateurDeCaractaire(int nbCaractaire);

//cette methode permet d'ajouter un role
//de locataire a un utilisateur
    void ajouterUtilsateurRole(User user);

//cette methode permet d'ajouter un role
//de bailleur a un user
    void ajouterUtilsateurRoleBailleur(User user);




}
