package com.example.immolocation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

@SpringBootApplication
public class ImmolocationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImmolocationApplication.class, args);
        Calendar calendar = Calendar.getInstance();

        System.out.println("Current Date = " + calendar.getTime());
        // Incrementing Month by 2
        calendar.add(Calendar.MINUTE, 5);
        System.out.println("Updated Date (+2 Months) = " + calendar.getTime());

        Date date =new Date();
        BCryptPasswordEncoder crypte= new BCryptPasswordEncoder();
        String mot_de_passe_code= crypte.encode("123" );
        System.out.println(mot_de_passe_code);
        System.out.println( LocalDateTime.now());

    }

}
