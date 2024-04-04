package ec.lab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.lab.domain.User;
import ec.lab.service.bean.UserBean;

@RestController
public class UserController {

   @Autowired
   private UserBean userBean;

    @GetMapping("/get/{username}")
    public User predict(@PathVariable String username) {
    	System.out.println(username);
        return userBean.getUser(username);
    }
    
    @PostMapping("/add_details")
    public ResponseEntity<User> create(User user){
    	if(userBean.exists(user.getUsername())) {
    		return ResponseEntity.badRequest().build();
    	}
        User createdUser = userBean.save(user);
        if(createdUser == null) {
        	return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(createdUser);
    }

}
