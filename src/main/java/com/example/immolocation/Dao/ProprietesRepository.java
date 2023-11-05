package com.example.immolocation.Dao;

import com.example.immolocation.Model.Bailleur;
import com.example.immolocation.Model.Proprietes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProprietesRepository extends JpaRepository<Proprietes, Long>{

    public Proprietes findById(long id_propriete);

    public Proprietes findByName(String name);

   /* @Query("update from propriete where ")
    public void updatePropriete(Propriete propriete);*/

    public List<Proprietes> findAllByBailleur(Bailleur bailleur);
    // Pageable findAllByBailleur(Bailleur bailleur);

    @Query("select p from  Proprietes p where p.prix >:x and p.prix <:y ")
    public List<Proprietes> finfAllByPrix(@Param("x") Long prixInf, @Param("y")Long prixSup);

   // @Query("select p from  Proprietes p where p.ville like :x ")
    //public List<Proprietes> finfAllByRegion(@Param("x")String region);

    @Query("select p from  Proprietes p where p.Localisation like :x")
    public List<Proprietes> finfAllByLocalisation(@Param("x")String localisation);

    //public Page<Propriete> listProprieteParBailleur






}

