package ec.lab.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ec.lab.domain.User;
import ec.lab.domain.UserPreferences;
import ec.lab.service.bean.UserBean;
import ec.lab.service.bean.UserPrefBean;

@Controller
public class UserController {
	
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


   @Autowired
   private UserBean userBean;
   
   @Autowired
   private UserPrefBean userPrefBean;
 

    @GetMapping("/get/{username}")
    public User predict(@PathVariable String username) {
    	System.out.println(username);
        return userBean.getUser(username);
    }
    
    @PostMapping("/add_details")
    public String create(User user, Model model){
        model.addAttribute("userPref", new UserPreferences());
        user.setImage("https://i.pinimg.com/564x/c0/27/be/c027bec07c2dc08b9df60921dfd539bd.jpg");
        User createdUser = userBean.save(user);
        if(createdUser == null) {
        	return "error";
        }
        return "preferences";
    }
    @PostMapping("/save_prefs")
    public String create(UserPreferences userPref){
    	userPref.setUsername(userBean.getUserName());
    	UserPreferences createdUser = userPrefBean.save(userPref);
        if(createdUser == null) {
        	return "error";
        }
        return "home";
    }
    @GetMapping("/edit_user_details_form")
    public String editUserdetails(Model model){
        model.addAttribute("user", userBean.getUser(userBean.getUserName()));
      
        return "user_details_edit";
    }
    @GetMapping("/edit_user_pref_form")
    public String editUserPrefdetails(Model model){
        model.addAttribute("userPref", userPrefBean.getUserPref(userBean.getUserName()));
        return "preferences_edit";
    }
    @PostMapping("/edit_user_details")
    public String editUserdetails(User user, Model model){
    	User editUser = userBean.getUser(userBean.getUserName());
		editUser.setAge(user.getAge());
    	editUser.setSex(user.getSex());
    	editUser.setOrientation(user.getOrientation());
    	editUser.setBodyType(user.getBodyType());
    	editUser.setDiet(user.getDiet());
    	editUser.setDrinks(user.getDrinks());
    	editUser.setDrugs(user.getDrugs());
    	editUser.setEducation(user.getEducation());
    	editUser.setEthnicity(user.getEthnicity());
    	editUser.setHeight(user.getHeight());
    	editUser.setLocation(user.getLocation());
    	editUser.setJob(user.getJob());
    	editUser.setSmokes(user.getSmokes());
    	editUser.setBio(user.getBio());


        
    	userBean.save(editUser);
      
        return "home";
    }
    @PostMapping("/edit_prefs")
    public String editUserPrefs(UserPreferences userPref){
    	UserPreferences editUser = userPrefBean.getUserPref(userBean.getUserName());
    	editUser.setAge(userPref.getAge());
    	editUser.setBodyType(userPref.getBodyType());
    	editUser.setDiet(userPref.getDiet());
    	editUser.setDrinks(userPref.getDrinks());
    	editUser.setDrugs(userPref.getDrugs());
    	editUser.setEducation(userPref.getEducation());
    	editUser.setEthnicity(userPref.getEthnicity());
    	editUser.setHeight(userPref.getHeight());
    	editUser.setLocation(userPref.getLocation());
    	editUser.setJob(userPref.getJob());
    	editUser.setSmokes(userPref.getSmokes());

    	userPrefBean.save(editUser);
        
        return "home";
    }


}
