package ec.lab.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import ec.lab.domain.RegisteredUser;
import ec.lab.domain.User;
import ec.lab.service.bean.LoginRegistrationSevice;
import ec.lab.service.bean.UserBean;

@Controller
public class LoginRegistrationController {

   @Autowired
   private LoginRegistrationSevice service;
   @Autowired
   private UserBean userService;

   private static final Logger logger = LoggerFactory.getLogger(LoginRegistrationController.class);

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
        try {
        	List<User> loadUsers = userService.parseUsersFromFile();
        	for(User u: loadUsers) {
        		userService.save(u);
        	}
        	}catch(Exception e){
        	}
        return "registration_form";
    }
    
    @GetMapping("/home")
    public String homeScreen(Model model) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String name = auth.getName();
    	System.out.println(name);
    	RegisteredUser user = service.getUser(name);
    	if(userService.getUser(user.getUsername())!=null) {
            return "home";
    	}
    	User userDetails = new User();
    	userDetails.setUsername(user.getUsername());
    	userDetails.setFirstName(user.getFirstName());
    	userDetails.setLastName(user.getLastName());

    	
        model.addAttribute("user", userDetails);

    	return "user_details";
    }
    
//    @GetMapping("/logout")
//    public String logout() {
//    	SecurityContextHolder.clearContext();
//
//        // Invalidate the HTTP session
//        HttpSession session = request.getSession(false);
//        if (session != null) {
//            session.invalidate();
//        }
//    	
//    	return "login";
//    }
//   
}
