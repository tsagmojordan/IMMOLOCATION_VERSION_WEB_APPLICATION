package com.example.immolocation.Controleur;

import com.example.immolocation.Dao.BailleurRepository;
import com.example.immolocation.Dao.LocataireRepository;
import com.example.immolocation.Model.Locataire;
import com.example.immolocation.Model.User;
import com.example.immolocation.Service.IBailleurServices;
import com.example.immolocation.Service.ILocataireServices;
import com.example.immolocation.Service.IUserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MessageController {
    @Autowired
    BailleurRepository bailleurRepository;
    @Autowired
    LocataireRepository locataireRepository;
@Autowired
    ILocataireServices iLocataireServices;
@Autowired
IBailleurServices iBailleurServices;
@Autowired
    IUserServices iUserServices;

    @GetMapping("/messagerieBailleur")
    public String  messagerieBailleur(HttpServletRequest httpServletRequest, Model model){

        HttpSession httpSession= httpServletRequest.getSession();
        SecurityContext securityContext= (SecurityContext)
                httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
        String login =securityContext.getAuthentication().getName();

        List<Locataire> locataireList=iLocataireServices.findAllByBailleur(iBailleurServices.rechercherBailleur(login));
        model.addAttribute("listeDesLocataireDeBailleur",locataireList);



       // liste_loc_selon_Bailleurid
        System.out.println(login);
        System.out.println(locataireList);
        return "index1";
    }
    @GetMapping("/messagerieLocataire")
    public String  messagerieLocataire(){
        return "index2";
    }
}
