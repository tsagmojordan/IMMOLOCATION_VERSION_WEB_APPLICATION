package com.example.immolocation.Service;

import com.example.immolocation.Dao.UserRepository;
import com.example.immolocation.Model.User;
import com.example.immolocation.Security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

   @Autowired
    UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String Login) throws UsernameNotFoundException {
        User user= repo.findByLogin(Login);
        if(user==null) {
            throw new UsernameNotFoundException("ce user ne figure pas dans notre liste");
        }
        else
        {
            return new CustomUserDetails(user);
        }

    }


}
