package com.example.immolocation.Controleur;

import com.example.immolocation.Dao.BailleurRepository;
import com.example.immolocation.Dao.LocataireRepository;
import com.example.immolocation.Model.Bailleur;
import com.example.immolocation.Model.Facture;
import com.example.immolocation.Model.Locataire;
import com.example.immolocation.Model.User;
import com.example.immolocation.Service.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@AllArgsConstructor
public class Bailleur_controlleur {
   @Autowired
   ServiceFacture facture;
   @Autowired
   Bailleur bailleur;
   @Autowired
   ServiceFacture serviceFacture;
   @Autowired
   ILocataireServices iLocataireServices;
   @Autowired
    BailleurServiceImpl bailleurService;

    @Autowired
    IBailleurServices iBailleurServices;

    @Autowired
    IUserServices iUserServices;
    @Autowired
    BailleurRepository bailleurRepository;
    @Autowired
LocataireRepository locataireRepository;
    /*
        @GetMapping("/Ajouter_locataire")
        public String ajouter_locataire() {
            return "/Bailleur/AjoutLocataire"        }

       /* @GetMapping("/Bailleur/AuthentificationBailleur")
        public String authentification_locataire() {
           // facture.avertissement_dette();
            return "/Bailleur/Bailleur";
        }
  @Autowired
    Locataire locataire;
    @GetMapping("/Ajouter_locataire")
    public String ajouter_locataire() {
        return "/Bailleur/AjoutLocataire";
    }
   // @Secured(value="ROLE_BAILLEUR")

    @GetMapping("/Bailleur/AuthentificationBailleur")
    public String authentification_locataire(HttpServletRequest httpServletRequest) {
       // facture.avertissement_dette();
        HttpSession httpSession= httpServletRequest.getSession();
        SecurityContext securityContext= (SecurityContext)
        httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
        String login =securityContext.getAuthentication().getName();

        bailleur.setLogin("login");
        System.out.println(login);
        return "/Bailleur/Bailleur";
    }

        @GetMapping("/h")
        public String pro()
        {
            return "/propriete";
        }
    */


     @GetMapping("/factureBailleur")
    public String facturebail(Model model,HttpServletRequest httpServletRequest){

          HttpSession httpSession= httpServletRequest.getSession();
          SecurityContext securityContext= (SecurityContext)
                  httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
          String login =securityContext.getAuthentication().getName();
          this.bailleur=iBailleurServices.rechercherBailleur(login);
          List<Locataire> locataireList=iLocataireServices.findAllByBailleur(iBailleurServices.rechercherBailleur(login));
          List<Locataire> locataires= new ArrayList<>();

          locataires.addAll(bailleurService.locataireselonloginBailleur(login));
          model.addAttribute("listeDesLocataireDeBailleur",locataires);
          model.addAttribute("listeDesLocataireDeBailleur",locataireList);

          return "/Bailleur/Facturer";
      }

    public Bailleur bailleur(){
        return this.bailleur;
    }

    @GetMapping("/AjouterBailleur")
    public String AjouterBailleur(Model model) {
        model.addAttribute("Bailleur", new Bailleur());
        return "Bailleur/AjouterBailleur";
    }

    @PostMapping("/SaveBailleurProcessing")
    public String save(Model model, Bailleur bailleur, User user){
        model.addAttribute("user",new User());
        model.addAttribute("Bailleur",new Bailleur());
        user.setId(bailleur.getId());
        iUserServices.ajouterUtilsateurRoleBailleur(user);
        //bailleur.setLogin(user.getLogin());
        System.out.println(user.getId());
        iBailleurServices.ajouterBailleur(bailleur);

        return "redirect:/Bailleur/AuthentificationBailleur";
    }



    @RequestMapping("/locataire/facturer/{login}")
    public String proccederFacture(Model model, @PathVariable("login") String login) {


        Facture facture = serviceFacture.dernier_facture_loc(login);
        Locataire locataires = locataireRepository.chercher_loc_parLOGIN(login);
        model.addAttribute("listeDesLocataireDeBailleur", locataires);
        model.addAttribute("listeDesFactureDuLocTel", facture);
        model.addAttribute("bailleur",this.bailleur);
        locataires.setLogin(login);
        System.out.println(login);
        System.out.println(locataires);
        return "/Bailleur/fac_loc";
    }

}
