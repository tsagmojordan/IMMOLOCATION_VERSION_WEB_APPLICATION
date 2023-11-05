package com.example.immolocation.Dao;

import com.example.immolocation.Model.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FactureRepository extends JpaRepository<Facture, Long> {

    @Query("SELECT a FROM Facture a inner join a.locataire b WHERE b.login= ?1")
    List<Facture> liste_facture(String login);

    @Query("SELECT a FROM Facture a inner JOIN a.locataire r where r.login=?1  order by a.id_facture desc " )
                        List<Facture> dernier_facture_loc(String login);

    @Query("SELECT a.avance FROM Facture a inner join a.locataire b WHERE b.login= ?1 order by a.id_facture desc")
    List<String>  dernier_avance_enregistre(String login);

    @Query("SELECT a.dernier_montant_Enregistre FROM Facture a inner join a.locataire b WHERE b.login= ?1 order by a.id_facture desc")
  List<String>  dernier_montant_enregistre(String login);

    @Query("SELECT a.dette FROM Facture a inner join a.locataire b WHERE b.login= ?1 order by a.id_facture desc")
    List<String>  dernier_dette_enregistre(String login);

    @Query("SELECT a.surplus FROM Facture a inner join a.locataire b WHERE b.login= ?1 order by a.id_facture desc")
    List<String>  dernier_surplus_enregistre(String login);


    @Query("SELECT a.avance FROM Facture a  WHERE a.id_facture=?1")
    int  avance_enregistre_fac(int id);

    @Query("SELECT a.dernier_montant_Enregistre FROM Facture a  WHERE a.id_facture=?1 ")
    int  montant_enregistre_fac(int id);

    @Query("SELECT a.dette FROM Facture a  WHERE a.id_facture=?1")
    int  dette_enregistre_fac(int id);

    @Query("SELECT a.surplus FROM Facture a WHERE a.id_facture=?1 ")
    int  surplus_enregistre_fac(int id);

    @Query("SELECT a.date_Facturation FROM Facture a  WHERE a.id_facture=?1")
    String date_enregistre_fac(int id);



    @Query("SELECT a.montant FROM Facture a inner join a.locataire b WHERE b.login= ?1 order by a.id_facture desc")
    List<Integer>  montant(String login);

}


