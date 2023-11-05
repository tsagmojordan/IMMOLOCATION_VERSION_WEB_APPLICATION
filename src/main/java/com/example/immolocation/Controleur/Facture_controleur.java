package com.example.immolocation.Controleur;

import com.example.immolocation.Dao.FactureRepository;
import com.example.immolocation.Dao.LocataireRepository;
import com.example.immolocation.Model.Bailleur;
import com.example.immolocation.Model.Facture;
import com.example.immolocation.Model.Locataire;
import com.example.immolocation.Service.PdfService;
import com.example.immolocation.Service.ServiceFacture;
import com.example.immolocation.Service.pdfServicegeneral;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@Controller
@AllArgsConstructor
public class Facture_controleur {
    @Autowired
    Bailleur bailleur;
    @Autowired
    ServiceFacture serviceFacture;
    @Autowired
    LocataireRepository locataireRepository;
    @Autowired
       Locataire locataire;
    @Autowired
    PdfService pdfService;
    @Autowired
    FactureRepository factureRepository;
    @Autowired
    pdfServicegeneral pdfServicegeneral;

    @GetMapping("/factureLocataire")
    public String factureloc() {
        return "/Locataire/Facture";
    }


    @GetMapping("/factureLocataire1")
    public String factureloc12() {



        return "/Locataire/Facture";
    }


    @PostMapping(value = "/form")
    public String facturer( @RequestParam int montant) {
         serviceFacture.attribuer_fact(locataire.getLogin(), montant);
         System.out.println(serviceFacture.Liste_de_facture(locataire.getLogin()));
        return "Accueil";
    }
@GetMapping("/telecahrgerFacture")
    public String telechargerpdf(HttpServletResponse response1, HttpServletRequest httpServletRequest) throws IOException {
        response1.setContentType("application/pdf");
        DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
        String currentDate = dateFormater.format(new Date());
        String headerkey ="content-Disposition";
        String headerValue="attachement;filename=FACTURE"+ currentDate +".pdf";
        response1.setHeader(headerkey,headerValue);

    HttpSession httpSession= httpServletRequest.getSession();
    SecurityContext securityContext= (SecurityContext)
            httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
    String login =securityContext.getAuthentication().getName();
     locataire.setLogin(login);


    pdfService.facture_pdf(response1,login);
        return "/Locataire/Facture";
    }
@GetMapping("/facture")
    public String telecharger_facture(Model model,HttpServletRequest httpServletRequest){

    HttpSession httpSession= httpServletRequest.getSession();
    SecurityContext securityContext= (SecurityContext)
            httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
    String login =securityContext.getAuthentication().getName();


    System.out.println(login);
    System.out.println(factureRepository.liste_facture(login));
    System.out.println(locataireRepository.montantMensuel(login));

    model.addAttribute("listeFacture",factureRepository.liste_facture(login));
    model.addAttribute("montantMensuel",locataireRepository.montant_mentuel(login));

        return "/Locataire/Facture";
}

    @PostMapping ("/enregistrerfacture/{login}")
    public String enregistre_facture(@RequestParam int montant , @PathVariable String login){
        System.out.println(montant);
        System.out.println(login);
      serviceFacture.attribuer_fact(login,montant);

        return "/Bailleur/Facturer";
    }
    @GetMapping("/Facture/historique/{id}")
    public String telechargerpdfgeneral(HttpServletResponse response1, HttpServletRequest httpServletRequest, @PathVariable("id") int id ) throws IOException {
        response1.setContentType("application/pdf");
        DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
        String currentDate = dateFormater.format(new Date());
        String headerkey ="content-Disposition";
        String headerValue="attachement;filename=FACTURE"+ currentDate +".pdf";
        response1.setHeader(headerkey,headerValue);

        HttpSession httpSession= httpServletRequest.getSession();
        SecurityContext securityContext= (SecurityContext)
                httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
        String login =securityContext.getAuthentication().getName();


       System.out.println(id);
       pdfServicegeneral.facture_pdfGeneral(response1,id);

        return "/Locataire/Facture";
    }



}
