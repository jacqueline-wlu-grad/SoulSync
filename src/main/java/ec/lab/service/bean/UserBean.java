
package ec.lab.service.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ec.lab.domain.RegisteredUser;
import ec.lab.domain.User;
import ec.lab.repo.UserRepository;

@Service
public class UserBean {

	@Autowired
	private UserRepository userDao;

	@Autowired
	private LoginRegistrationSevice service;

	public User getUser(String username) {
		List<User> users = userDao.findByUsername(username);
		// TODO Auto-generated method stub
		if (!CollectionUtils.isEmpty(users))
			return users.get(0);

		return null;
	}

	public User save(User user) {
		// TODO Auto-generated method stub
		return userDao.save(user);
	}

	public boolean exists(String username) {
		if (getUser(username) != null)
			return true;
		// TODO Auto-generated method stub
		return false;
	}

	public List<User> parseUsersFromFile() {
		String filePath = "users.txt";
		List<User> users = new ArrayList<>();

		try {
			// Create ObjectMapper instance
			ObjectMapper objectMapper = new ObjectMapper();

			// Read JSON data from file and parse into array of User objects
			User[] usersArray = objectMapper.readValue(new File(filePath), User[].class);

			// Convert array to list
			for (User user : usersArray) {
				users.add(user);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return users;
	}

	public String getUserName() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		System.out.println(name);
		RegisteredUser user = service.getUser(name);
		String username = user.getUsername();
		return username;
	}

}