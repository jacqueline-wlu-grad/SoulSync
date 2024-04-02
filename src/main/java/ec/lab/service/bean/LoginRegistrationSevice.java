
package ec.lab.service.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

import ec.lab.domain.RegisteredUser;
import ec.lab.domain.User;
import ec.lab.repo.RegisteredUserRepository;
import ec.lab.repo.UserRepository;

@Service
public class LoginRegistrationSevice{

@Autowired
   private RegisteredUserRepository registeredUserDao;


	public RegisteredUser getUser(String username) {
		return registeredUserDao.findByUsername(username);
		// TODO Auto-generated method stub
		
	}
	public RegisteredUser save(RegisteredUser user) {
		// TODO Auto-generated method stub
		return registeredUserDao.save(user);
	}
	public boolean exists(String username) {
		if(getUser(username)!= null)
			return true;
		// TODO Auto-generated method stub
		return false;
	}
}