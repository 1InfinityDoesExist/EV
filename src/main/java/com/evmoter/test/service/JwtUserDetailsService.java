package com.evmoter.test.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.evmoter.test.dao.UserDao;
import com.evmoter.test.dao.UserRegistrationRepository;
import com.evmoter.test.model.DAOUser;
import com.evmoter.test.model.UserDTO;
import com.evmoter.test.model.UserRegistrationPage;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRegistrationRepository userRepo;
    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	DAOUser user = userDao.findByUsername(username);
	if (user == null) {
	    throw new UsernameNotFoundException("User not found with username: " + username);
	}
	return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
		new ArrayList<>());
    }

    public ResponseEntity<?> save(UserDTO user) {
	DAOUser newUser = new DAOUser();
	newUser.setUsername(user.getUsername());
	newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
	newUser.setEmail(user.getEmail());
	newUser.setMobilePhone(user.getMobilePhone());

	DAOUser duplicateChecker = userDao.findByEmailAndMoblie(user.getEmail(), user.getMobilePhone());
	if (duplicateChecker != null) {
	    return new ResponseEntity<String>("Duplicate UserName Constraint", HttpStatus.BAD_REQUEST);
	}

	DAOUser userDaoToDB = userDao.save(newUser);
	if (userDaoToDB != null) {
	    UserRegistrationPage pg = new UserRegistrationPage();
	    pg.setEmail(userDaoToDB.getEmail());
	    pg.setMobile(userDaoToDB.getMobilePhone());
	    pg.setPassword(userDaoToDB.getPassword());
	    UserRegistrationPage ans = userRepo.save(pg);
	}

	return new ResponseEntity<DAOUser>(userDaoToDB, HttpStatus.OK);
    }
}