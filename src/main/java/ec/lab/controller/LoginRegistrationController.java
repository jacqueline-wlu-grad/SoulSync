package ec.lab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.lab.domain.RegisteredUser;
import ec.lab.service.bean.LoginRegistrationSevice;

@RestController
public class LoginRegistrationController {

   @Autowired
   private LoginRegistrationSevice service;

    
    @PostMapping("/process_register")
    public String processRegister(RegisteredUser user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
         
        service.save(user);
         
        return "register_success";
    }

}
