
package ec.lab.service.bean;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import ec.lab.domain.User;
import ec.lab.domain.UserPreferences;
import ec.lab.repo.UserPrefRepository;

@Service
public class UserPrefBean{

@Autowired
   private UserPrefRepository userPrefDao;


	public UserPreferences getUserPref(String username) {
		List<UserPreferences> users = userPrefDao.findByUsername(username);
		// TODO Auto-generated method stub
		if(!CollectionUtils.isEmpty(users))
			return users.get(0);
		
		return null;
	}
	public UserPreferences save(UserPreferences user) {
		// TODO Auto-generated method stub
		return userPrefDao.save(user);
	}
	public boolean exists(String username) {
		if(getUserPref(username)!= null)
			return true;
		// TODO Auto-generated method stub
		return false;
	}
}