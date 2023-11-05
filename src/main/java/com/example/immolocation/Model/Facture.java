package com.example.immolocation.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Facture {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id_facture;
    private String date_Facturation= LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    private String dernier_montant_Enregistre;
    private  int montant=0 ;
    private int surplus=0;
    private int avance=0;
    private int dette=0;

    // liaison de tables

  @ManyToOne
  @JoinColumn(name="login_Locataire")
  private Locataire locataire;
  // getters et setters

    //public void attribuer_Facture(Locataire locataire){


}
