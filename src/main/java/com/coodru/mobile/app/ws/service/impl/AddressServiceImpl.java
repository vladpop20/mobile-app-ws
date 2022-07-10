package com.coodru.mobile.app.ws.service.impl;

import com.coodru.mobile.app.ws.io.entity.AddressEntity;
import com.coodru.mobile.app.ws.io.entity.UserEntity;
import com.coodru.mobile.app.ws.io.repository.AddressRepository;
import com.coodru.mobile.app.ws.io.repository.UserRepository;
import com.coodru.mobile.app.ws.service.AddressService;
import com.coodru.mobile.app.ws.shared.dto.AddressDto;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

	private final UserRepository userRepository;
	private final AddressRepository addressRepository;

	public AddressServiceImpl(UserRepository userRepository, AddressRepository addressRepository) {
		this.userRepository = userRepository;
		this.addressRepository = addressRepository;
	}

	@Override public List<AddressDto> getAddresses(String userId) {
		List<AddressDto> returnList = new ArrayList<>();

		UserEntity user = userRepository.findByUserId(userId);
		if(user == null) {
			throw new UsernameNotFoundException("User with ID: " + userId + " not found!");
		}

		List<AddressEntity> addresses = addressRepository.findAllByUserDetails(user);
		addresses.forEach(address -> {
			returnList.add(new ModelMapper().map(address, AddressDto.class));
		});

		return returnList;
	}

	@Override public AddressDto getAddress(String addressId, String userId) {
		AddressDto returnValue = null;

		UserEntity user = userRepository.findByUserId(userId);
		if(user == null) {
			throw new UsernameNotFoundException("User with ID: " + userId + " not found!");
		}

		AddressEntity address = addressRepository.findByAddressIdAndUserDetails(addressId, user);
		if (address != null) {
			returnValue = new ModelMapper().map(address, AddressDto.class);
		}

		return returnValue;
	}
}
