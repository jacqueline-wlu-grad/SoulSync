package ec.lab.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import ec.lab.service.bean.UserBean;
import ec.lab.service.bean.UserPrefBean;

@RestController
public class ModelController {
	
    private static final Logger logger = LoggerFactory.getLogger(ModelController.class);


   @Autowired
   private ModelBean model;
   @Autowired
   private LoginRegistrationSevice service;
   @Autowired
	private UserPrefBean userPrefBean;
   
   @Autowired
   private UserBean userBean;

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
    	String sex = userBean.getUser(user.getUsername()).getSex();
    	String orientation = userBean.getUser(user.getUsername()).getOrientation();
        return model.topMatches(userPrefBean.getUserPref(user.getUsername()), sex, orientation);
    }
     
    

}
