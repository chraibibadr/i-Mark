package com.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.model.Login;
import com.auth.model.User;
import com.auth.reposetory.UserRepository;


@RestController
@RequestMapping("/auth")
public class Controller {
	@Autowired
	UserRepository ur;
	@PostMapping("/register")
	public void ajouter(@RequestBody User u) {
		ur.save(u);
	}
	@PostMapping
	public User log (@RequestBody Login l) {
		User u= ur.findByUsername(l.getUsername());
		if(u!=null) {
			if(u.getPassword().equals(l.getPassword())) {
					return u;}
					
				
		}
		return null;
		
			
	}
	
	
	

}
