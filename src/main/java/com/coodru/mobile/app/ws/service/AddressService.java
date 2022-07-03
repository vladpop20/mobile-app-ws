package com.coodru.mobile.app.ws.service;

import com.coodru.mobile.app.ws.shared.dto.AddressDto;

import java.util.List;

public interface AddressService {
	List<AddressDto> getAddresses(String userId);

	AddressDto getAddress(String addressId);
}
