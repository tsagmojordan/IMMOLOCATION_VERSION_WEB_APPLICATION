package com.example.immolocation.Controleur;

import com.example.immolocation.Model.*;
import com.example.immolocation.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class  Locataire_controleur {
    @Autowired
    UserService service;
    @Autowired
    ILocataireServices iLocataireServices;

    @Autowired
    IBailleurServices iBailleurServices;

    @Autowired
    IProprietesServices iProprietesServices;

   private String login;
    private Bailleur bailleur;
    ProprietesController proprietesController=new ProprietesController();


    @GetMapping("/Locataire/AuthentificationLocataire")
    public String authentificationlocataire() {
        return "/Locataire/AuthentificationLocataire";
    }



    // @Secured(value="ROLE_LOCATAIRE") Faudra retirer les commentaires une fois qu'on poura ajouter un locataire avec son role
    @GetMapping("/locataire")
    public String locataire(HttpServletRequest httpServletRequest) {
       HttpSession httpSession = httpServletRequest.getSession();
        SecurityContext securityContext = (SecurityContext)
                httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
        String login = securityContext.getAuthentication().getName();
      //
        return "/Locataire/Locataire";
    }

    @GetMapping("/en")
    public String enre() {
        return "en_lo";
    }


    @PostMapping(value = "/val")
    public String ajouterUserRole(User user) {
        service.ajouterUtilsateurRole(user);
        return "Accueil";
    }


    @GetMapping("/AjouterLocataire")
    public String formulaireLocataire(Model model,HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession();
        SecurityContext securityContext = (SecurityContext)
                httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
        String login = securityContext.getAuthentication().getName();
        this.bailleur= iBailleurServices.rechercherBailleur(login);
        model.addAttribute("bailleur",this.bailleur);
        model.addAttribute("locataire", new Locataire());
        List<Proprietes> proprieteList = iProprietesServices.proprieteLibreParBailleur(this.bailleur);
        model.addAttribute("proprieteList",proprieteList);
        return "Bailleur/AjouterLocataire";
    }

    @PostMapping("/SaveLocataire")
    public String save(Model model, Locataire locataire, @RequestParam("name") String name){
        model.addAttribute("locataire",new Locataire());
        Proprietes proprietes=iProprietesServices.findByName(name,this.bailleur);
        iLocataireServices.addLocataire(locataire,this.bailleur,proprietes);
        return "redirect:/GestionLocataire";
    }

    @RequestMapping("/deleteLocataire" )
    public String delete(String id){
            Locataire locataire=iLocataireServices.rechercherParId(id);
            List<Proprietes> proprietesListDuLocataire=locataire.getPropriete();
            if(proprietesListDuLocataire.size()<=1){
                Proprietes proprietes=proprietesListDuLocataire.get(0);
                proprietesListDuLocataire.remove(proprietes);
                iProprietesServices.setDisponibilite("LA PROPRIETE N'EST PAS OCCUPEE PAR UN LOCATAIRE",proprietes);
                iProprietesServices.modifierPropriete(proprietes.getId(),proprietes);
                iLocataireServices.deleteLocatire(locataire);

            }
            else
            {
                for(int i=0;i<proprietesListDuLocataire.size();i++){
                    Proprietes proprietes=proprietesListDuLocataire.get(i);
                    proprietesListDuLocataire.remove(proprietes);
                    iProprietesServices.setDisponibilite("LA PROPRIETE N'EST PAS OCCUPEE PAR UN LOCATAIRE",proprietes);
                    iProprietesServices.modifierPropriete(proprietes.getId(),proprietes);

                }

                iLocataireServices.deleteLocatire(locataire);
            }
            return "redirect:/GestionLocataire";

        }


    @GetMapping("/GestionLocataire")
    public String pageGestionLocataire(Model model,HttpServletRequest httpServletRequest){

        HttpSession httpSession = httpServletRequest.getSession();
        SecurityContext securityContext = (SecurityContext)
                httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
        String login = securityContext.getAuthentication().getName();
        this.bailleur= iBailleurServices.rechercherBailleur(login);
        System.out.println(login);


        List<Locataire> locataires= new ArrayList<>();
        locataires=iLocataireServices.findAllByBailleur(this.bailleur);


        model.addAttribute("locataireList",locataires);
        model.addAttribute("bailleur",this.bailleur);
        return "Bailleur/GestionLocataire";
    }

    @GetMapping("/updateLocataire")
    public String update(String id, Model model,HttpServletRequest httpServletRequest){
        HttpSession httpSession = httpServletRequest.getSession();
        SecurityContext securityContext = (SecurityContext)
                httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
        String login = securityContext.getAuthentication().getName();
        this.bailleur= iBailleurServices.rechercherBailleur(login);
        model.addAttribute("bailleur",this.bailleur);
        model.addAttribute("locataire",iLocataireServices.rechercherParId(id));
        List<Proprietes> proprieteList = iProprietesServices.proprieteLibreParBailleur(this.bailleur);
        model.addAttribute("proprieteList",proprieteList);
        return "Bailleur/AjouterLocataire";
    }
}
