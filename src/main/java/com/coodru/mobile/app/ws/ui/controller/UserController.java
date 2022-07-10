package com.coodru.mobile.app.ws.ui.controller;

import com.coodru.mobile.app.ws.service.AddressService;
import com.coodru.mobile.app.ws.service.UserService;
import com.coodru.mobile.app.ws.shared.RequestOperationName;
import com.coodru.mobile.app.ws.shared.RequestOperationStatus;
import com.coodru.mobile.app.ws.shared.dto.AddressDto;
import com.coodru.mobile.app.ws.shared.dto.UserDto;
import com.coodru.mobile.app.ws.ui.controller.model.request.UserDetailsRequestModel;
import com.coodru.mobile.app.ws.ui.controller.model.response.AddressRest;
import com.coodru.mobile.app.ws.ui.controller.model.response.OperationStatusModel;
import com.coodru.mobile.app.ws.ui.controller.model.response.UserRest;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "users")
public class UserController {

	private final UserService userService;
	private final AddressService addressService;

	public UserController(UserService userService, AddressService addressService) {
		this.userService = userService;
		this.addressService = addressService;
	}

	@GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "25") int limit) {

		List<UserRest> returnList = new ArrayList<>();
		List<UserDto> users = userService.getUsers(page, limit);
		ModelMapper modelMapper = new ModelMapper();

		users.forEach(userDto -> {
			UserRest userModel = modelMapper.map(userDto, UserRest.class);
			returnList.add(userModel);
		});

		return returnList;
	}

	// The default representation for response to be produced, is first in order, that means XML mediaType
	@GetMapping(path = "/{id}",
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserRest getUser(@PathVariable String id)	{
		ModelMapper modelMapper = new ModelMapper();

		UserDto userDto = userService.getUserByUserId(id);

		return modelMapper.map(userDto, UserRest.class);
	}

	@PostMapping(consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
			produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception {
		UserRest returnValue = new UserRest();
		ModelMapper modelMapper = new ModelMapper();

//		if (userDetails.getFirstName().isEmpty()){
//			throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
//		}

//		UserDto userDto = new UserDto();
//		BeanUtils.copyProperties(userDetails, userDto);

		UserDto userDto = modelMapper.map(userDetails, UserDto.class);

		UserDto createdUser = userService.createUser(userDto);
		returnValue = modelMapper.map(createdUser, UserRest.class);

		return returnValue;
	}

	@PutMapping(path = "/{id}",
			consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
			produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) {
		ModelMapper modelMapper = new ModelMapper();

		UserDto userDto = modelMapper.map(userDetails, UserDto.class);
		UserDto updatedUser = userService.updateUser(id, userDto);

		return modelMapper.map(updatedUser, UserRest.class);
	}

	@DeleteMapping(path = "/{id}",
			produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public OperationStatusModel deleteUser(@PathVariable String id) {
		OperationStatusModel returnValue = new OperationStatusModel();

		userService.deleteUser(id);

		returnValue.setOperationName(RequestOperationName.DELETE.name());
		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		return returnValue;
	}


	@GetMapping(path = "/{id}/addresses",
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public List<AddressRest> getUserAddresses(@PathVariable String id) {
		List<AddressRest> returnValue = new ArrayList<>();

		List<AddressDto> addressesDto = addressService.getAddresses(id);

		if (addressesDto != null && !addressesDto.isEmpty()) {
			Type listType = new TypeToken<List<AddressRest>>() {}.getType();
			returnValue = new ModelMapper().map(addressesDto, listType);
		}

		return returnValue;
	}

	@GetMapping(path = "/{id}/addresses/{addressId}",
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public AddressRest getUserAddress(@PathVariable String addressId, @PathVariable String id) {
//		It works even without using the 'id' pathVariable

		AddressDto addressDto = addressService.getAddress(addressId);

		return new ModelMapper().map(addressDto, AddressRest.class);
	}
}
