
package ec.lab.service.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import ec.lab.domain.User;
import ec.lab.repo.UserRepository;

@Service
public class UserBean{

@Autowired
   private UserRepository userDao;


	public User getUser(String username) {
		List<User> users = userDao.findByUsername(username);
		// TODO Auto-generated method stub
		if(!CollectionUtils.isEmpty(users))
			return users.get(0);
		
		return null;
	}
	public User save(User user) {
		// TODO Auto-generated method stub
		return userDao.save(user);
	}
	public boolean exists(String username) {
		if(getUser(username)!= null)
			return true;
		// TODO Auto-generated method stub
		return false;
	}
}