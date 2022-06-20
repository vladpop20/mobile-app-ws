package com.coodru.mobile.app.ws.service.impl;

import com.coodru.mobile.app.ws.io.entity.UserEntity;
import com.coodru.mobile.app.ws.repository.UserRepository;
import com.coodru.mobile.app.ws.service.UserService;
import com.coodru.mobile.app.ws.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override public UserDto createUser(UserDto user) {

		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);

		userEntity.setEncryptedPassword("P@ssW0rd");
		userEntity.setUserId("testUserID");

		UserEntity storedUserDetails = userRepository.save(userEntity);

		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(storedUserDetails, returnValue);

		return returnValue;
	}
}
