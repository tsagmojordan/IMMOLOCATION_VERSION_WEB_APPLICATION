package com.example.immolocation.Service;

import com.example.immolocation.Controleur.ProprietesController;
import com.example.immolocation.Dao.LocataireRepository;
import com.example.immolocation.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocataireServiceImpl implements ILocataireServices {

    @Autowired
    IBailleurServices iBailleurServices;
    @Autowired
    LocataireRepository locataireRepository;
    @Autowired
    IProprietesServices iProprietesServices;
    @Autowired
    IUserServices iUserServices;

    /*
    cette methode permet d'enregistrer un locataire
    en définissant la proprieté qu'il occupe comme
    non disponible et en definissant qui est le bailleur
    de ce locataire.elle retourne un string qui correspond
    au mot de passe du locataire.ce mot de passe sera remet
    au locataire via une api d'envoie de sms
     */
    @Override
    public String addLocataire(Locataire locataire,Bailleur bailleur,Proprietes propriete) {

        String mdp=iUserServices.GenerateurDeCaractaire(5);

        List<Proprietes> proprieteList = locataire.getPropriete();//lorsque on alloue une propriete elle devient occupé

        propriete=iProprietesServices.setDisponibilite("LA PROPRIETE EST OCCUPEE PAR UN LOCATAIRE",propriete);//changement d'etat de la propriete(passage de l'etat libre a occupé)

        User user=new User();//instanciation d'un nouvelle utilisateur

        user.setLogin(locataire.getContact());//definition du login de l'utilisateur

        user.setMot_de_passe(mdp);//on genère 5 caractère en guise de mot de passe

        iUserServices.ajouterUtilsateurRole(user);//ajouter le role locataire a l'utilisateur

        locataire.setLogin(locataire.getContact());//enregistrer le login de l'utilisateur

        proprieteList.add(propriete);

        locataire.setPropriete(proprieteList);//enregister sa proprieté

        locataire.setBailleur(bailleur);// definit le bailleur qui enregistre la propriete

        locataireRepository.save(locataire);//senregistrement du locataire

        iProprietesServices.modifierPropriete(propriete.getId(),propriete);//modification de la propriete le rendant occupée

        System.out.println(mdp);

        return mdp;
    }




    @Override
    public void deleteLocatire(Locataire locataire) {

        locataireRepository.delete(locataire);

    }

    @Override
    public List<Locataire> findAllByBailleur(Bailleur bailleur) {

        return  locataireRepository.findAllByBailleur(bailleur);

    }


    @Override
    public Locataire rechercherParPropriete(Proprietes propriete) {
       Locataire locataire=locataireRepository.findLocataireByPropriete(propriete);
       return locataire;
    }

    @Override
    public Locataire rechercherLocataire(String login) {
        return locataireRepository.chercher_loc_parLOGIN(login);
    }

    @Override
    public void modifierLocataire(Locataire locataire) {
        locataireRepository.save(locataire);
    }

    @Override
    public Locataire rechercherParId(String id) {
        return locataireRepository.findloc(id);
    }


}