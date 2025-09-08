package com.example.investmentsaggregator.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.investmentsaggregator.controller.dto.CreateUserDto;
import com.example.investmentsaggregator.controller.dto.UpdateUserDto;
import com.example.investmentsaggregator.entity.User;
import com.example.investmentsaggregator.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private UserService userService;
	
	private ArgumentCaptor<User> userArgumentCaptor;
	
	private ArgumentCaptor<UUID> uuidArgumentCaptor;
	
	
	@Nested
	class createUser{
		
		@Test
		@DisplayName("Should create a User with Success")
		void shouldCreateAUserWithSuccess() {
			
			//Arrange
			userArgumentCaptor = ArgumentCaptor.forClass(User.class);
			
			var user = new User(UUID.randomUUID(), "Rafael", "vetsi@gmail.com", "12345", Instant.now(),null);
			doReturn(user).when(userRepository).save(userArgumentCaptor.capture());
			var input = new CreateUserDto("Rafael", "vetsi@gmail.com", "12345");
			
			//Act
			var output = userService.createUser(input);
			
			//Assert
			assertNotNull(output);
			
			var userCaptured = userArgumentCaptor.getValue();
			
			assertEquals(input.username(), userCaptured.getUsername());
			assertEquals(input.email(), userCaptured.getEmail());
			assertEquals(input.password(), userCaptured.getPassword());
		}
		
		@Test
		@DisplayName("Should throw exception when error occurs")
		void shouldThrowExceptionWhenErrorOccurs() {
			
			//Arrange
			doThrow(new RuntimeException()).when(userRepository).save(any());
			var input = new CreateUserDto("Rafael", "vetsi@gmail.com", "12345");
			
			//Act & assert
			assertThrows(RuntimeException.class, () -> userService.createUser(input));
			
			
		}
		
	}
	
	@Nested
	class getuserById{
		
		@Test
		@DisplayName("Should get user by id with success when optional is present")
		void shouldGetUserByIdWithSuccessWhenOptionalIsPresent() {
			
			//Arrange
			uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
			
			var user = new User(UUID.randomUUID(), "Rafael", "vetsi@gmail.com", "12345", Instant.now(),null);
			
			doReturn(Optional.of(user)).when(userRepository).findById(uuidArgumentCaptor.capture());

			//Act
			var output = userService.getUserById(user.getUserId().toString());
			
			//Assert
			assertTrue(output.isPresent());
			assertEquals(user.getUserId(), uuidArgumentCaptor.getValue());
		}
		
		@Test
		@DisplayName("Should get user by id with success when optional is empty")
		void shouldGetUserByIdWithSuccessWhenOptionalIsEmpty() {
			
			//Arrange
			uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
			
			var userId = UUID.randomUUID();
			
			doReturn(Optional.empty()).when(userRepository).findById(uuidArgumentCaptor.capture());

			//Act
			var output = userService.getUserById(userId.toString());
			
			//Assert
			assertTrue(output.isEmpty());
			assertEquals(userId, uuidArgumentCaptor.getValue());
		}
		
	}

	@Nested
	class listUsers{
		
		@Test
		@DisplayName("Should return all users with success")
		void shouldReturnAllUsersWithSuccess() {
			
			//Arrange
			var user = new User(UUID.randomUUID(), "Rafael", "vetsi@gmail.com", "12345", Instant.now(),null);
			var userList = List.of(user);
			doReturn(userList).when(userRepository).findAll();
			
			//Act
			var output = userService.getAllUsers();
			
			//Assert
			assertNotNull(output);
			assertEquals(userList.size(), output.size());
			
		}
		
	}
	
	@Nested
	class deleteById{
		
		@Test
		@DisplayName("Should delete user with success when user exists")
		void shouldDeleteUserWithSuccessWhenUserExists(){
			//Arrange
			uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
			
			
			doReturn(true).when(userRepository).existsById(uuidArgumentCaptor.capture());
			
			doNothing().when(userRepository).deleteById(uuidArgumentCaptor.capture());
			
			var userId = UUID.randomUUID();

			//Act
			userService.deleteUser(userId.toString());
			
			//Assert
			var idList = uuidArgumentCaptor.getAllValues();
			assertEquals(userId, idList.get(0));
			assertEquals(userId, idList.get(1));
			
			verify(userRepository, times(1)).existsById(idList.get(0));
			verify(userRepository, times(1)).deleteById(idList.get(1));
		}
		
		@Test
		@DisplayName("Should not delete user when user does not exist")
		void shouldNotDeleteUserWhenUserDoesNotExist(){
			//Arrange
			uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
			
			
			doReturn(false).when(userRepository).existsById(uuidArgumentCaptor.capture());
			
			
			var userId = UUID.randomUUID();

			//Act
			userService.deleteUser(userId.toString());
			
			//Assert
			assertEquals(userId, uuidArgumentCaptor.getValue());
			
			verify(userRepository, times(1)).existsById(uuidArgumentCaptor.getValue());
			verify(userRepository, times(0)).deleteById(any());
		}
		
	}
	
	@Nested
	class updateUser{
		@Test
		@DisplayName("Should update user by id when user exists and username or password are filled")
		void shouldUpdateUserWhenUserExistsAndUsernameOrPasswordAreFilled() {
			
			//Arrange
			uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
			userArgumentCaptor = ArgumentCaptor.forClass(User.class);
			
			var updateUserDto = new UpdateUserDto("newUsername", "newPassword");
			
			var user = new User(UUID.randomUUID(), "Rafael", "vetsi@gmail.com", "12345", Instant.now(),null);
			
			doReturn(Optional.of(user)).when(userRepository).findById(uuidArgumentCaptor.capture());
			doReturn(user).when(userRepository).save(userArgumentCaptor.capture());

			//Act
			userService.updateUser(user.getUserId().toString(), updateUserDto);
			
			//Assert
			assertEquals(user.getUserId(), uuidArgumentCaptor.getValue());
			
			var userSaved = userArgumentCaptor.getValue();
			
			assertEquals(updateUserDto.username(), userSaved.getUsername());
			assertEquals(updateUserDto.password(), userSaved.getPassword());
			
			verify(userRepository, times(1)).findById(uuidArgumentCaptor.getValue());
			verify(userRepository, times(1)).save(user);
		}
		
		@Test
		@DisplayName("Should not update user when user does not exist")
		void shouldNotUpdateUserWhenUserNotExist() {
			
			//Arrange
			uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
			userArgumentCaptor = ArgumentCaptor.forClass(User.class);
			
			var updateUserDto = new UpdateUserDto("newUsername", "newPassword");
			var userId = UUID.randomUUID();
			
			doReturn(Optional.empty()).when(userRepository).findById(uuidArgumentCaptor.capture());

			//Act
			userService.updateUser(userId.toString(), updateUserDto);
			
			//Assert
			assertEquals(userId, uuidArgumentCaptor.getValue());
			
			verify(userRepository, times(1)).findById(uuidArgumentCaptor.getValue());
			verify(userRepository, times(0)).save(any());
		}
	}
	
	
}
