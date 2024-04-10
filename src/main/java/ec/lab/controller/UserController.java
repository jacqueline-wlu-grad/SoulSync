package ec.lab.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import ec.lab.domain.RegisteredUser;
import ec.lab.domain.User;
import ec.lab.domain.UserPreferences;
import ec.lab.service.bean.LoginRegistrationSevice;
import ec.lab.service.bean.UserBean;
import ec.lab.service.bean.UserPrefBean;

@Controller
public class UserController {

   @Autowired
   private UserBean userBean;
   
   @Autowired
   private UserPrefBean userPrefBean;
   
   @Autowired
   private LoginRegistrationSevice service;

    @GetMapping("/get/{username}")
    public User predict(@PathVariable String username) {
    	System.out.println(username);
        return userBean.getUser(username);
    }
    
    @PostMapping("/add_details")
    public String create(User user, Model model){
        model.addAttribute("userPref", new UserPreferences());
        
        User createdUser = userBean.save(user);
        if(createdUser == null) {
        	return "error";
        }
        return "preferences";
    }
    @PostMapping("/save_prefs")
    public String create(UserPreferences userPref){
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String name = auth.getName();
    	System.out.println(name);
    	RegisteredUser user = service.getUser(name);
    	userPref.setUsername(user.getUsername());
    	UserPreferences createdUser = userPrefBean.save(userPref);
        if(createdUser == null) {
        	return "error";
        }
        return "home";
    }
    
    @GetMapping("/load")
    public List<User> load() {
    	List<User> loadUsers = userBean.parseUsersFromFile();
    	for(User u: loadUsers) {
    		userBean.save(u);
    	}
        return loadUsers;
        
    }

}
