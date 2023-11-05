package com.example.immolocation.Dao;


import com.example.immolocation.Model.Bailleur;
import com.example.immolocation.Model.Locataire;
import com.example.immolocation.Model.Proprietes;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Component
@Repository
public interface LocataireRepository extends CrudRepository<Locataire, String> {

    @Query("select u.login from Locataire u inner join u.factures r where r.id_facture=?1 ")
    String recupererloginLocataire(int id_facture);

    @Query("select u.nom_Locataire from Locataire u where u.login=?1 ")
    String non_locataire( String login);
    @Query("select u.Profession from Locataire u where u.login=?1 ")
    String prefession_locataire( String login);
    @Query("select u.contact from Locataire u where u.login=?1 ")
    String contact_locataire( String login);

    @Query("select u.montant_mensuel_a_payer from Locataire u where u.login=?1 ")
    String montantMensuel( String login);


    @Query("select u.date_entree_locataire from Locataire u where u.login=?1 ")
    LocalDateTime dateDentre(String login);


    @Query("Select r.nom_bailleur from Locataire u inner join u.bailleur r where u.login =?1 ")
                    String nom_Bailleur(String login);

    @Query("Select r.prenom_bailleur from Locataire u inner join u.bailleur r where u.login =?1 ")
    String prenom_Bailleur(String login);

    @Query("Select r.telephone from Locataire u inner join u.bailleur r where u.login =?1 ")
    String contact_Bailleur(String login);

    @Query("Select r.description from Locataire u inner join u.propriete r where u.login =?1 ")
    String description_propriete(String login);

    @Query("Select r.Localisation from Locataire u inner join u.propriete r where u.login =?1 ")
    String localisation_propriete(String login);


    @Query("SELECT u.montant_mensuel_a_payer FROM Locataire u WHERE u.login=?1")
               long montant_mentuel(String login);
      

                      List<Locataire> findAllByBailleur(Bailleur bailleur);

               public Locataire findLocataireByPropriete(Proprietes propriete);

               @Query("select u from Locataire u  where u.login=?1")
                  Locataire chercher_loc_parLOGIN(String  login);

                 public List<Locataire> findAllByPropriete(Proprietes propriete);

                Locataire findByPropriete(Proprietes propriete);

                @Query("select u from Locataire u where u.login=?1")
                   Locataire findloc(String id);

                List<Locataire> findAll();

    @Query("select u from Bailleur r inner join r.locataire u where r.id=?1")
    List<Locataire> liste_loc_selon_Bailleurid(long id);

    @Query("select u from Locataire u where u.login=?1")
    Locataire retourlocataire(String login);

    @Query("SELECT a.nom_Locataire, a.montant_mensuel_a_payer,r.dette FROM Locataire a " +
            "inner JOIN a.factures r WHERE r.dette>0 AND r.id_facture=(select max(r.id_facture) from r  ) ")
                            List<String> locataire_endettes();

    @Query("Select u from Locataire u inner join u.bailleur r where u.login =?1 ")
    String contact_Bailleurst(String login);

}
