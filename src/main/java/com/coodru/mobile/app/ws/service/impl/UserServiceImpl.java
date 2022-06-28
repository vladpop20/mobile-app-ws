package com.coodru.mobile.app.ws.service.impl;
import com.coodru.mobile.app.ws.io.entity.UserEntity;
import com.coodru.mobile.app.ws.io.repository.UserRepository;
import com.coodru.mobile.app.ws.service.UserService;
import com.coodru.mobile.app.ws.shared.Utils;
import com.coodru.mobile.app.ws.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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

		if(userRepository.findByEmail(user.getEmail()) != null) {
			throw new RuntimeException("Record already exists");
		}

		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);

		String publicUserId = utils.generateUserId(25);
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
			throw new UsernameNotFoundException(userId);
		}

		BeanUtils.copyProperties(user, returnValue);
		return returnValue;
	}

	@Override public UserDto updateUser(String id, UserDto userDto) {
		return null;
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
