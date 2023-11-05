package com.example.immolocation.Controleur;

import com.example.immolocation.Model.Role;
import com.example.immolocation.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

public class User_controleur {

    @Autowired
    UserDetailsService userDetailsService;
    private Role role;
    private User userConnecté;

   /* @RequestMapping("/Authentification")
    public String AuthitificationPage (Model model) {
        model.addAttribute("user",new User());
        return "Bailleur/AuthentificationBailleur";
    }
    @PostMapping("/authentificationUser")
    public String Authentification(Model model,User user){
        model.addAttribute("user",new User());
        UserDetails userBD=userDetailsService.loadUserByUsername(user.getLogin());
        if(userBD.getPassword().equals(user.getMot_de_passe())){
            if(role.getRole_id()==1l){

            }
        }return "";
    }*/
}
