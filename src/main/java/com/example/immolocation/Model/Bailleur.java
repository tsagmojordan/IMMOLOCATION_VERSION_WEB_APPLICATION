package com.example.immolocation.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Component
public class Bailleur  implements Serializable   {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String nom_bailleur;
    private String prenom_bailleur;
    private String Numero_cni;
    private String telephone;


    @OneToOne
    private User user;
    @OneToMany(mappedBy="bailleur")
    private Collection<Proprietes> propriete;

    @OneToMany()
    private List<Locataire> locataire =new ArrayList<Locataire>();

}
