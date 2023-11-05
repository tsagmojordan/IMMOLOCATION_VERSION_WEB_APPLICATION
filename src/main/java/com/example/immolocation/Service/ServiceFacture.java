package com.example.immolocation.Service;

import com.example.immolocation.Dao.FactureRepository;
import com.example.immolocation.Dao.LocataireRepository;
import com.example.immolocation.Model.Facture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.Math.abs;

@Service
@Component
public class ServiceFacture {
    @Autowired
    LocataireRepository locataireRepository;

    @Autowired
    private FactureRepository factureRepository;

    //***********************************************essaie de traiment sur la robustesse du code lorsqu'un locataire n'a aucune facture précédente pour la premiere facturation***************************

    //retoure la liste des facture selon les id
    public Facture dernier_facture_loc(String login){//************************************traitement en cas de premiere facturation
        List<Facture> factureList=factureRepository.dernier_facture_loc(login);
        if(factureList.isEmpty()){
            return new Facture();
        }else {
            return factureRepository.dernier_facture_loc(login).get(0);
        }

    }

    public List<Facture> Liste_de_facture(String login) {

       return factureRepository.liste_facture(login);
    }


    public int derniere_dette_loc(String login) {

        List<Facture> test= factureRepository.dernier_facture_loc(login);//************************************traitement en cas de premiere facturation
        if(test.isEmpty()){
            return 0;
        }else{
            return  test.get(0).getDette();
        }


    }


    public int dernier_avance_loc(String login) {

        List<Facture> test= factureRepository.dernier_facture_loc(login);//************************************traitement en cas de premiere facturation
            if(test.isEmpty()){
                return 0;
            }else{
                return  test.get(0).getAvance();
            }

    }


    public int dernier_surplus_loc(String login) {
        List<Facture> test= factureRepository.dernier_facture_loc(login);//************************************traitement en cas de premiere facturation
        if(test.isEmpty()){
            return 0;
        }else{
            return  test.get(0).getSurplus();
        }



    }
    //methode permetant de facture elle attriebut une facture facture a un locatiare

    public void attribuer_fact(String longin, int montant)
    {
        Facture facture= new Facture();
        long montant_mensuel=locataireRepository.montant_mentuel(longin);

       if( derniere_dette_loc(longin)>0 && dernier_avance_loc(longin)==0)
       {
           int soustraction_dette_montant=derniere_dette_loc(longin)-montant;
           int soustraction_montant_dette=montant-derniere_dette_loc(longin);


           if (soustraction_dette_montant==0) {
               facture.setMontant(0);
               facture.setDette((int) montant_mensuel);
           }

         if( locataireRepository.montant_mentuel(longin)==soustraction_dette_montant)
          {
           facture.setDette(soustraction_dette_montant);

        }

           if( locataireRepository.montant_mentuel(longin)==soustraction_montant_dette)
           {
               facture.setMontant(soustraction_montant_dette);
            }


           if ( montant_mensuel>soustraction_montant_dette){
               facture.setAvance( abs(soustraction_montant_dette + dernier_avance_loc(longin)));
               facture.setDette((int) (montant_mensuel-soustraction_montant_dette));
           }

           if( montant_mensuel<soustraction_montant_dette){
               facture.setMontant((int) locataireRepository.montant_mentuel(longin));
               facture.setSurplus((int) (soustraction_dette_montant-montant_mensuel));
           }

       }

        if(derniere_dette_loc(longin)==0 && dernier_surplus_loc(longin)==0){
            if (montant_mensuel>montant){
                //facture.setAvance(montant);
                facture.setDette((int) abs(montant_mensuel-montant));
            }
            else if (montant_mensuel<montant){
                facture.setSurplus((int) abs(montant-montant_mensuel));
                facture.setMontant((int) montant_mensuel);
            }
        }


        ////////////////////////////valide
        if(derniere_dette_loc(longin)==0 && dernier_surplus_loc(longin)>0){
           long avance =dernier_surplus_loc(longin)-montant_mensuel;
            int  somme_montant_avance = (int) (avance+montant);
            if(avance<0){
                        if( somme_montant_avance<montant_mensuel ){
                            facture.setAvance( somme_montant_avance );
                            facture.setDette((int) (montant_mensuel-somme_montant_avance));
                        }
                        else if(  somme_montant_avance ==montant_mensuel){
                            facture.setMontant(somme_montant_avance);
                        }
                        else if(somme_montant_avance>montant_mensuel){
                            facture.setMontant((int) montant_mensuel);
                            facture.setSurplus((int) (somme_montant_avance-montant_mensuel));
                        }
            }
            else if(avance==0){
                facture.setMontant((int) montant_mensuel);
                facture.setSurplus(montant);
            }
            else if(avance>0){
                facture.setMontant((int) montant_mensuel);
                facture.setSurplus((int) (avance+montant));
            }

        }
        if(dernier_avance_loc(longin)>0 && derniere_dette_loc(longin)==0) {

          int somme_avance_montant = dernier_avance_loc(longin) + montant;
          if (somme_avance_montant==montant_mensuel){
              facture.setMontant(somme_avance_montant);
          }
          else if(montant_mensuel>somme_avance_montant) {
              facture.setMontant(0);
              facture.setDette((int) (montant_mensuel-somme_avance_montant));
          }
          else if(montant_mensuel<somme_avance_montant) {
              facture.setMontant((int) montant_mensuel);
              facture.setSurplus((int) (somme_avance_montant - montant_mensuel));
          }



        }
                  facture.setDernier_montant_Enregistre(String.valueOf(montant));
                  facture.setLocataire(locataireRepository.chercher_loc_parLOGIN(longin));

                  factureRepository.save(facture);
    }
}


  /*      Locataire locataire =locataireRepository.findById(id);
            facture.setLocataire(locataire);
          if (montant == (int) locataireRepository.montant_mentuel(id)){
              factureRepository.save(facture);
          }
          else if (montant < (int) locataireRepository.montant_mentuel(id))
          {
              facture.setDette((int) locataireRepository.montant_mentuel(id)-montant);
              facture.setAvance(montant);
              facture.setMontant(0);
              factureRepository.save(facture);
          }else
          {
            facture.setSurplus(montant-(int)locataireRepository.montant_mentuel(id));
              factureRepository.save(facture);
          }
            facture.setMontant(montant);
           factureRepository.save(facture);



    public void avertissement_dette() {
        chrono.schedule(new TimerTask() {

            @Override
            public void run() {

            }
        }, 700000000, 70000000);
    }
*/
