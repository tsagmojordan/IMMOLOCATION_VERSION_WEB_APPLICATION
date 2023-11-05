package com.example.immolocation;

import com.example.immolocation.Dao.BailleurRepository;
import com.example.immolocation.Dao.LocataireRepository;
import com.example.immolocation.Dao.ProprietesRepository;
import com.example.immolocation.Model.*;
import com.example.immolocation.Service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SpringBootTest
class ImmolocationApplicationTests {


    @Autowired
    LocataireRepository locataireRepository;
    @Autowired
    BailleurRepository bailleurRepository;

    @Autowired
    IProprietesServices iProprietesServices;

    @Autowired
    IBailleurServices iBailleurServices;

    @Autowired
    ProprietesRepository proprietesRepository;

    @Autowired
    ILocataireServices iLocataireServices;
    @Autowired
    Bailleur bailleur;

    @Autowired
    ServiceFacture serviceFacture;

    @Test
    public void save() {

        /*Bailleur bailleur=new Bailleur("alex","deparie",21,"marvel","ankara");
        bailleurRepository.save(bailleur);*/
       /* Propriete propriete1 = new Propriete("tres enorme","ankara","loundesk",true,1600);
        e.ajouterProprieter(propriete1,bailleur);
        System.out.println(propriete1.getBailleur().toString());*/

    }

    @Test
    public void ListlocTest() {
        List<Locataire> locataires = iBailleurServices.locataireselonloginBailleur("jordan");
        List<Locataire> locataireList = iLocataireServices.findAllByBailleur(iBailleurServices.rechercherBailleur("jordan"));
        System.out.println(locataires.size());
        System.out.println(locataireList.size());
    }

    @Test
    public void AddLocataire() {
        Locataire locataire = new Locataire();
        Proprietes propriete = new Proprietes();
        List<Proprietes> proprietes = iProprietesServices.proprieteLibreParBailleur(iBailleurServices.rechercherBailleur("marc"));
        propriete = proprietes.get(0);
        iLocataireServices.addLocataire(locataire, iBailleurServices.rechercherBailleur("marc"), propriete);

    }

    @Test
    public void orderByDate() {
        List<Proprietes> proprietes = iProprietesServices.findAllFreePropriete();
        System.out.println(proprietes.size());
    }

    @Test
    public void findByName() {
        Bailleur bailleur = iBailleurServices.rechercherBailleur("marc");
        Proprietes propriete = iProprietesServices.findByName("X²", bailleur);
        System.out.println(propriete.toString());
    }

    @Test
    public void listLocataire() {
        List<Locataire> locataires = new ArrayList<>();
        this.bailleur = iBailleurServices.rechercherBailleur("al");
        List<Proprietes> proprietes = iProprietesServices.listProprieteparBailleur(this.bailleur);
        for (int i = 0; i < proprietes.size(); i++) {
            locataires.add(iLocataireServices.rechercherParPropriete(proprietes.get(i)));
        }
        System.out.println(locataires.size());

    }

    @Test
    public void TestNotification() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 1);
        int datePayementLocataire = 24;
        int a = 1;
        //

        while (a != 5) {
            int date = new Date().getSeconds();
            Long tiers = System.currentTimeMillis();
            if (date == datePayementLocataire && tiers == 10) {
                System.out.println("vous devez payez aujourd'hui");
            }

        }
    }

    @Test
    public void TestNotif2() {
        List<Locataire> locataireList = iLocataireServices.findAllByBailleur(iBailleurServices.rechercherBailleur("ft"));
        //lancement du compteur


        int a = 1;
        //parcours de la liste
        for (Locataire l : locataireList) {
            while (a ==1) {
                int DateEntrerLoc =10;
                int DateActuelle = 10;
                int tempsIntervalle=DateEntrerLoc-DateActuelle;
                if (DateEntrerLoc - DateActuelle == 0) {
                    System.out.println("Mr/Mme " + l.getNom_Locataire() + "the limit payment date in today ");
                } else if (DateEntrerLoc - DateActuelle < 0) {
                    System.out.println("Mr/Mme " + l.getNom_Locataire() + "you are suppose to pay in" +tempsIntervalle +"day(s)");
                } else if (DateEntrerLoc - DateActuelle > 0) {
                    System.out.println("votre Dette etait de " + serviceFacture.derniere_dette_loc(l.getLogin()));
                    serviceFacture.attribuer_fact(l.getLogin(),l.getMontant_mensuel_a_payer());
                    System.out.println("votre Dette est maintenant de " + serviceFacture.derniere_dette_loc(l.getLogin()));
                    System.out.println("Mr/Mme " + l.getNom_Locataire() + "vous avez un retard de payement de " +serviceFacture.derniere_dette_loc(l.getLogin()));

                }
            }


        }


    }

    @Test
    public void findprop() {
        List<Proprietes> proprieteList = iProprietesServices.listProprieteparBailleur(bailleurRepository.findById(1L));
        System.out.println(proprieteList.get(0).toString());

    }

    @Test
    public void findpropriete() {
        List<Proprietes> proprieteList = iProprietesServices.listProprieteparBailleur(bailleurRepository.findById(1L));
        for (int i = 0; i < proprieteList.size(); i++) {
            System.out.println(proprieteList.get(i).toString());

        }
        Proprietes propriete = proprieteList.get(5);
        iProprietesServices.supprimerPropriete(propriete.getId());
        System.out.println("vous avez supprimez la propriete suivante:" + propriete.toString());
    }

    @Test
    public void Save() {
        //Propriete propriete=new Propriete("hoolé","makarie","eozuj",false,50,bailleurRepository.findById(2L).get());
        //iProprieteServices.ajouterProprieter(propriete,bailleurRepository.findById(2L).get());

    }

    @Test
    public void consulterPropriete() {

        Proprietes propriete = proprietesRepository.findById(9L);
        System.out.println(propriete.toString());


    }

    @Test
    public void bailleur() {
        Bailleur bailleur = bailleurRepository.findById(1L);
        System.out.println(bailleur.toString());
    }


    @Test
    public void publier() {
        List<Proprietes> proprieteList = iProprietesServices.findAllFreePropriete();
      /* List<Image> imageList=new ArrayList<>();
        for(int i=0;i<proprieteList.size();i++){
            imageList=iimageServices.RechercherParPropriete(proprieteList.get(i));

        }*/
        //List<Propriete> proprieteList1=proprieteRepository.findAll();
        for (int j = 0; j < iProprietesServices.findAllFreePropriete().size(); j++) {
            Proprietes propriete = iProprietesServices.findAllFreePropriete().get(j);
            proprieteList.add(propriete);
            System.out.println("numero du bailleur: " + proprieteList.get(j).getBailleur().getTelephone());

            System.out.println("description : " + proprieteList.get(j).getDescription());

            System.out.println("locaalisation: " + proprieteList.get(j).getLocalisation());

            System.out.println("prix de la propriete: " + proprieteList.get(j).getPrix());
        }
    }

    @Test
    public void publier2() {
        List<Proprietes> proprieteList = iProprietesServices.findAllFreePropriete();
       /* List<Image> imageList=new ArrayList<>();

        for(int i=0;i<proprieteList.size();i++){
            imageList=iimageServices.RechercherParPropriete(proprieteList.get(i));

        }*/
        //List<Propriete> proprieteList1=proprieteRepository.findAll();
        for (int j = 0; j < iProprietesServices.findAllFreePropriete().size(); j++) {
            //imageList=iimageServices.RechercherParPropriete(proprieteList.get(j));

            System.out.println("numero du bailleur: " + proprieteList.get(j).getBailleur().getTelephone());

            System.out.println("description : " + proprieteList.get(j).getDescription());

            System.out.println("locaalisation: " + proprieteList.get(j).getLocalisation());

            System.out.println("prix de la propriete: " + proprieteList.get(j).getPrix());
        }

    }
}



