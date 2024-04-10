package ec.lab.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ec.lab.domain.RegisteredUser;
import ec.lab.domain.User;
import ec.lab.repo.UserPrefRepository;
import ec.lab.service.bean.LoginRegistrationSevice;
import ec.lab.service.bean.ModelBean;
import ec.lab.service.bean.UserPrefBean;

@RestController
public class ModelController {

   @Autowired
   private ModelBean model;
   @Autowired
   private LoginRegistrationSevice service;
   @Autowired
	private UserPrefBean userPrefBean;

    @GetMapping("/predict/{score}")
    public double predict(@PathVariable String score) {
    	System.out.println(score);
        return model.prediction(score);
    }

     @GetMapping("/matches")
    public List<User> matches() {
    	
    	
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String name = auth.getName();
    	System.out.println(name);
    	RegisteredUser user = service.getUser(name);
    	
        return model.topMatches(userPrefBean.getUserPref(user.getUsername()));
    }
     
    

}
