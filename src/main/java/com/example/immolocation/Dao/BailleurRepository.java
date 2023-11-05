package com.example.immolocation.Dao;


import com.example.immolocation.Model.Bailleur;
import com.example.immolocation.Model.Locataire;
import com.example.immolocation.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface  BailleurRepository extends JpaRepository<Bailleur, Long> {

        @Query("select u from Bailleur r inner join r.locataire u where r.login=?1")
        List<Locataire> liste_loc_selon_Bailleurlogin(String id);


        @Query("select u from Bailleur r inner join r.locataire u where r.id=?1")
        List<Locataire> liste_loc_selon_Bailleurid(long id);

        @Query("select u from Bailleur u where u.login=?1")
        Bailleur retoureBailleur(String login);

        @Query("select u.nom_bailleur from Bailleur u where u.login=?1")
        String nomBailleur(String login);
        Bailleur findById(long id);

        @Query("select u.id from Bailleur u where u.login=?1")
        long retoureBailleurID(String login);


}
