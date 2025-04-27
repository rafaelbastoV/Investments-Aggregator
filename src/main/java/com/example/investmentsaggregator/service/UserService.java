package com.example.investmentsaggregator.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.investmentsaggregator.controller.CreateUserDto;
import com.example.investmentsaggregator.controller.UpdateUserDto;
import com.example.investmentsaggregator.entity.User;
import com.example.investmentsaggregator.repository.UserRepository;

@Service
public class UserService {
	
	private final UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public UUID createUser(CreateUserDto createUserDto) {
		var entity = new User(UUID.randomUUID(),createUserDto.username(), createUserDto.email(), createUserDto.password(), Instant.now(), null);
		var userSaved = userRepository.save(entity);
		return userSaved.getUserId();
	}
	
	public Optional<User> getUserById(String userId) {
		var user = userRepository.findById(UUID.fromString(userId));
		return user;	
	}
	
	public List<User> getAllUsers() {
		var users = userRepository.findAll();
		return users;	
	}
	
	public void deleteUser(String userId) {
		var id = UUID.fromString(userId);
		if(userRepository.existsById(id)) {
			userRepository.deleteById(id);
		}
	}
	
	public void updateUser(String userId, UpdateUserDto updateUserDto) {
		var id = UUID.fromString(userId);
		var userEntity = userRepository.findById(id);
		if(userEntity.isPresent()) {
			var user = userEntity.get();
			
			if(updateUserDto.username() != null) {
				user.setUsername(updateUserDto.username());
			}
			if(updateUserDto.password() != null) {
				user.setPassword(updateUserDto.password());
			}
			
			userRepository.save(user);
		}
	}
	
}
