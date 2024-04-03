package ec.lab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.lab.domain.RegisteredUser;
import ec.lab.service.bean.LoginRegistrationSevice;

@Controller
public class LoginRegistrationController {

   @Autowired
   private LoginRegistrationSevice service;

    
    @PostMapping("/process_register")
    public String processRegister(RegisteredUser user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
         
        service.save(user);
         
        return "login";
    }
    
    @GetMapping("/login")
    public String login() {
      return "login";
    }
    
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registereduser", new RegisteredUser());
         
        return "registration_form";
    }
   
}
