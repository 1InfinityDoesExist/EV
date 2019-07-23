package com.evmoter.test.controller;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.evmoter.test.dao.UserDao;
import com.evmoter.test.dao.UserRegistrationRepository;
import com.evmoter.test.model.Address;
import com.evmoter.test.model.DAOUser;
import com.evmoter.test.model.UserRegistrationPage;
import com.evmoter.test.util.AddressUtil;
import com.evmoter.test.util.ReflectionUtil;

@RestController
@CrossOrigin
@RequestMapping(path = "/object/userRegistationPage")
public class UserRegistrationRestController {

    ReflectionUtil refUtil = ReflectionUtil.getInstance();
    @Autowired
    private UserRegistrationRepository userRepo;

    @Autowired
    private UserDao daoUser;

    @PostMapping(path = "/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationPage user,
	    @RequestParam(value = "userName") String userName, @RequestParam(value = "password") String password,
	    BindingResult result) {

	DAOUser d = daoUser.findByUsername(userName);
	if (d == null) {
	    return new ResponseEntity<String>("Sorry No User Available for this UserName", HttpStatus.BAD_REQUEST);
	}
	if (result.hasErrors()) {
	    Map<String, String> error = new LinkedHashMap<String, String>();
	    for (FieldError fieldError : result.getFieldErrors()) {
		error.put(fieldError.getField(), fieldError.getDefaultMessage());
	    }
	    return new ResponseEntity<Map<String, String>>(error, HttpStatus.BAD_REQUEST);

	}

	// populate geocode for addresses
	List<Address> createAddresses = user.getAddress();
	if (createAddresses != null) {
	    List<Address> addrList = AddressUtil.populateGeocode(createAddresses, "CREATE");
	    user.setAddress(addrList);
	}
	UserRegistrationPage userToDB = userRepo.save(user);
	return new ResponseEntity<UserRegistrationPage>(userToDB, HttpStatus.OK);

    }

    @GetMapping(path = "/get")
    public ResponseEntity<?> getAllRegisteredUsers() {
	List<UserRegistrationPage> urp = userRepo.getAllRegisteredUser();
	if (urp == null) {
	    return new ResponseEntity<String>("Sorry No Client Found Till Date", HttpStatus.BAD_REQUEST);
	}
	return new ResponseEntity<List<UserRegistrationPage>>(urp, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<?> deleteUserByMobileNumber(@RequestParam(name = "phone") String phoneNumber) {
	UserRegistrationPage userFromDB = userRepo.getRegisteredUserByPhoneNumber(phoneNumber);
	if (userFromDB == null) {
	    return new ResponseEntity<String>("No Client with Phone Number" + " " + phoneNumber,
		    HttpStatus.BAD_REQUEST);
	}
	userRepo.softDelete(phoneNumber);
	return new ResponseEntity<String>("Successfully Deleted The User with phoneNumber" + " " + phoneNumber,
		HttpStatus.OK);
    }

    @PatchMapping(path = "/update")
    public ResponseEntity<?> updateUserProfile(@Valid @RequestBody String userData,
	    @RequestParam(value = "email") String email, @RequestParam(value = "phone") String phone)
	    throws ParseException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
	    java.text.ParseException {

	Boolean flag = userData.contains("dob");
	UserRegistrationPage uRP = userRepo.getUserByEmailAndPhone(email, phone);
	if (uRP == null) {
	    return new ResponseEntity<String>("No data found for this email and phone", HttpStatus.BAD_REQUEST);
	}
	JSONParser parser = new JSONParser();
	JSONObject obj = (JSONObject) parser.parse(userData);
	for (Iterator iterator = ((Map<String, String>) obj).keySet().iterator(); iterator.hasNext();) {
	    String props = (String) iterator.next();
	    if (props.equals("dob")) {
		if (obj.get("dob") != null) {

		} else {
		    uRP.setDob(null);
		}
	    }

	    else if (props.equals("address")) {
		if (obj.get("address") != null) {
		    JSONArray jsonArray = (JSONArray) obj.get("address");
		    if (uRP.getAddress() == null) {

			List<Address> listAddress = new ArrayList<Address>();
			for (int iter = 0; iter < jsonArray.size(); iter++) {
			    JSONObject jsonobject = (JSONObject) jsonArray.get(iter);
			    Address addr = new Address();
			    for (Object str : jsonobject.keySet()) {
				String addrString = (String) str;
				refUtil.getSetterMethod("Address", addrString).invoke(addr, jsonobject.get(addrString));
			    }
			    listAddress.add(addr);
			}
			System.out.println(listAddress);
			List<Address> addrList = AddressUtil.populateGeocode(listAddress, "CREATE");
			uRP.setAddress(addrList);
			continue;
		    }
		    for (int iter = 0; iter < jsonArray.size(); iter++) {
			JSONObject jsonobject = (JSONObject) jsonArray.get(iter);
			for (Object str : jsonobject.keySet()) {
			    String addrString = (String) str;
			    refUtil.getSetterMethod("Address", addrString).invoke(uRP.getAddress().get(iter),
				    jsonobject.get(addrString));
			}
		    }
		    uRP.setAddress(AddressUtil.populateGeocode(uRP.getAddress(), "UPDATE"));
		} else {
		    uRP.setAddress(null);
		}
	    } else {
		refUtil.getSetterMethod("UserRegistrationPage", props).invoke(uRP, obj.get(props));
	    }
	}
	// Parse Date to store it in Database...!!!
	if (flag == true) {
	    String str = (String) obj.get("dob");

	    Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(str);
	    uRP.setDob(date1);
	}

	uRP.setDeleteFlag(false);
	uRP.setStatus("Registered");

	return new ResponseEntity<UserRegistrationPage>(userRepo.save(uRP), HttpStatus.OK);
    }
}
