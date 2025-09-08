package com.example.investmentsaggregator.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.investmentsaggregator.controller.dto.AccountResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.investmentsaggregator.controller.dto.CreateAccountDto;
import com.example.investmentsaggregator.controller.dto.CreateUserDto;
import com.example.investmentsaggregator.controller.dto.UpdateUserDto;
import com.example.investmentsaggregator.entity.Account;
import com.example.investmentsaggregator.entity.BillingAddress;
import com.example.investmentsaggregator.entity.User;
import com.example.investmentsaggregator.repository.AccountRepository;
import com.example.investmentsaggregator.repository.BillingAddressRepository;
import com.example.investmentsaggregator.repository.UserRepository;

@Service
public class UserService {
	
	private final UserRepository userRepository;
	private final AccountRepository accountRepository;
	private final BillingAddressRepository billingAddressRepository;
	
	public UserService(UserRepository userRepository, AccountRepository accountRepository, BillingAddressRepository billingAddressRepository) {
		this.userRepository = userRepository;
		this.accountRepository = accountRepository;
		this.billingAddressRepository = billingAddressRepository;
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
	
	public void createAccount(String userId, CreateAccountDto createAccountDto) {
		
		User user = userRepository.findById(UUID.fromString(userId))
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		
		var account = new Account(
			user,
			createAccountDto.description(),
			null,
			new ArrayList<>()
		);
		
		var accountCreated = accountRepository.save(account);
		
		var billingAddress = new BillingAddress(accountCreated.getAccountId(), accountCreated, createAccountDto.street(),
							createAccountDto.number());
		
		billingAddressRepository.save(billingAddress);
		
	}

    public List<AccountResponseDto> listAccounts(String userId) {
        User user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return user.getAccounts()
                .stream()
                .map(ac -> new AccountResponseDto(ac.getAccountId().toString(), ac.getDescription()))
                .toList();
    }
}
