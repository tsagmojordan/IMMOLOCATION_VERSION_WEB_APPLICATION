package com.example.immolocation.Security;

import com.example.immolocation.Service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.CachingUserDetailsService;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
 class BailleurWebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource ;


       @Bean
        public UserDetailsService userDetailsService() {
            return new CustomUserDetailsService();
        }

        @Bean
        public BCryptPasswordEncoder passwordEncoder () {
            return new BCryptPasswordEncoder();
        }
        @Bean
        public DaoAuthenticationProvider authenticationProvider () {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
        @Override
        protected void configure (AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(authenticationProvider());
            auth.jdbcAuthentication().dataSource(dataSource)
 .usersByUsernameQuery("select login as principal,mot_de_passe as credentials,true from users" +
                            " where login=?")
.authoritiesByUsernameQuery("select users_login as principal,roles_role as role from " +
        "User_Role where users_login=? ").rolePrefix("ROLE_");

        }

        @Override
        protected void configure (HttpSecurity http) throws Exception {


            http

                    .authorizeRequests().antMatchers(



                            "/SaveLocataire","/SaveBailleurProcessing","/image/imageDetails","/AjouterBailleur","/publication/**","/bootstrap-5.1.3-dist/**","/home","/proprietes","/authentification", "/h2/**", "/form", "/en", "/moi", "/val", "/", "/css/**", "/images/**", "/image/**").permitAll()

                    .anyRequest().authenticated()  // (1)
                    .and()
                    .formLogin().loginPage("/authentification").failureUrl("/login-error").and()
                    .logout().logoutUrl("/home").logoutSuccessUrl("/");

            http.csrf().disable();
            http.headers().frameOptions().disable();

        }
/*
.loginPage("/Bailleur/AuthentificationBailleur").permitAll()
   http.authorizeRequests().antMatchers("/h2/**", "/css/**", "/images/**")
                .authenticated()
                ;
                                        .and()
                                                                                        .logout().logoutSuccessUrl("/home").permitAll();
                                     .and()
                .formLogin()
                .usernameParameter("Login").defaultSuccessUrl("/home")
                .permitAll()
                ;           .logout().permitAll();

.antMatchers("").permitAll().antMatchers("/home").authenticated()

    }.and()
                .logout().logoutSuccessUrl("/home").permitAll();*/




}