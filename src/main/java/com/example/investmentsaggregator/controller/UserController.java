package com.example.investmentsaggregator.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.investmentsaggregator.entity.User;
import com.example.investmentsaggregator.service.UserService;

@RestController
@RequestMapping("/v1/users")
public class UserController {
	
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody CreateUserDto createUserDto){
		var userId = userService.createUser(createUserDto);
		return ResponseEntity.created(URI.create("/v1/users/" + userId.toString())).build();
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<User> getUserById(@PathVariable("userId") String userId){
		var user = userService.getUserById(userId);
		if(user.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user.get());
	}
	
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers(){
		var users = userService.getAllUsers();		
		return ResponseEntity.ok(users);
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<Object> deleteUser(@PathVariable("userId") String userId){
		userService.deleteUser(userId);		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<Object> updateUser(@PathVariable("userId") String userId, 
											@RequestBody UpdateUserDto updateUserDto){
		userService.updateUser(userId, updateUserDto);
		return ResponseEntity.noContent().build();
	}
	
}
