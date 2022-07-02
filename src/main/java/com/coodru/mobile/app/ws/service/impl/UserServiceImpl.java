package com.coodru.mobile.app.ws.service.impl;
import com.coodru.mobile.app.ws.exceptions.UserServiceException;
import com.coodru.mobile.app.ws.io.entity.UserEntity;
import com.coodru.mobile.app.ws.io.repository.UserRepository;
import com.coodru.mobile.app.ws.service.UserService;
import com.coodru.mobile.app.ws.shared.Utils;
import com.coodru.mobile.app.ws.shared.dto.UserDto;
import com.coodru.mobile.app.ws.shared.ErrorMessages;
import com.coodru.mobile.app.ws.ui.controller.model.response.UserRest;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final Utils utils;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public UserServiceImpl(UserRepository userRepository, Utils utils, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.utils = utils;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	public UserDto createUser(UserDto user) {

		// This is how we make sure that the email is UNIQUE, or we can do it directly in UserEntity
		if(userRepository.findByEmail(user.getEmail()) != null) {
			throw new RuntimeException("Record already exists");
		}

		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);

		String publicUserId = utils.generateId(25);
		userEntity.setUserId(publicUserId);

		// This is how the password was encrypted before it's stored in the DB
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userEntity.setUserId(publicUserId);

		UserEntity storedUserDetails = userRepository.save(userEntity);

		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(storedUserDetails, returnValue);

		return returnValue;
	}

	@Override public UserDto getUser(String email) {
		UserEntity userEntity = userRepository.findByEmail(email);

		if(userEntity == null) {
			throw new UsernameNotFoundException(email);
		}

		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(userEntity, returnValue);

		return returnValue;
	}

	@Override public UserDto getUserByUserId(String userId) {
		UserDto returnValue = new UserDto();
		UserEntity user = userRepository.findByUserId(userId);

		if(user == null) {
			throw new UsernameNotFoundException("User with ID: " + userId + " not found!");
		}

		BeanUtils.copyProperties(user, returnValue);
		return returnValue;
	}

	@Override public UserDto updateUser(String userId, UserDto user) {
		UserDto returnValue = new UserDto();
		UserEntity userEntity = userRepository.findByUserId(userId);

		if(userEntity == null) {
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		}

		userEntity.setFirstName(user.getFirstName());
		userEntity.setLastName(user.getLastName());
		userRepository.save(userEntity);

		BeanUtils.copyProperties(userEntity, returnValue);
		return returnValue;
	}

	@Override public void deleteUser(String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId);

		if(userEntity == null) {
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		}

		userRepository.delete(userEntity);
	}

	@Override public List<UserDto> getUsers(int page, int limit) {
		List<UserDto> returnList = new ArrayList<>();

		Pageable pageableRequest = PageRequest.of(page, limit);

		Page<UserEntity> userPage = userRepository.findAll(pageableRequest);
		List<UserEntity> users = userPage.getContent();

		users.forEach(user -> {
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(user, userDto);
			returnList.add(userDto);
		});

		return returnList;
	}

	/*	This method is used by Spring, to load a user from DB, using in this case, it's email
		and this method will be used in the process of user Sign-in
	*/
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByEmail(email);

		if(user == null) {
			throw new UsernameNotFoundException(email);
		}

		return new User(user.getEmail(), user.getEncryptedPassword(), new ArrayList<>());
	}
}
