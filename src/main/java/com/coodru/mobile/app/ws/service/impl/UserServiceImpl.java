package com.coodru.mobile.app.ws.service.impl;

import com.coodru.mobile.app.ws.io.entity.UserEntity;
import com.coodru.mobile.app.ws.repository.UserRepository;
import com.coodru.mobile.app.ws.service.UserService;
import com.coodru.mobile.app.ws.shared.Utils;
import com.coodru.mobile.app.ws.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final Utils utils;

	public UserServiceImpl(UserRepository userRepository, Utils utils) {
		this.userRepository = userRepository;
		this.utils = utils;
	}

	@Override public UserDto createUser(UserDto user) {

		if(userRepository.findByEmail(user.getEmail()) != null) {
			throw new RuntimeException("Record already exists");
		}

		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);

		String publicUserId = utils.generateUserId(25);
		userEntity.setEncryptedPassword("P@ssW0rd");
		userEntity.setUserId(publicUserId);

		UserEntity storedUserDetails = userRepository.save(userEntity);

		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(storedUserDetails, returnValue);

		return returnValue;
	}
}
