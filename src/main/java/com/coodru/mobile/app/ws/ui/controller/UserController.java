package com.coodru.mobile.app.ws.ui.controller;

import com.coodru.mobile.app.ws.service.UserService;
import com.coodru.mobile.app.ws.shared.dto.UserDto;
import com.coodru.mobile.app.ws.ui.controller.model.request.UserDetailsRequestModel;
import com.coodru.mobile.app.ws.ui.controller.model.response.ErrorMessages;
import com.coodru.mobile.app.ws.ui.controller.model.response.UserRest;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	// The default representation for response to be produced, is first in order, that means XML mediaType
	@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public UserRest getUser(@PathVariable String id)	{
		UserRest returnValue = new UserRest();

		UserDto userDto = userService.getUserByUserId(id);
		BeanUtils.copyProperties(userDto, returnValue);

		return returnValue;
	}

	@PostMapping(consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
			produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception {
		UserRest returnValue = new UserRest();

		if (userDetails.getFirstName().isEmpty()){
			throw new Exception(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		}

		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);

		UserDto createdUser = userService.createUser(userDto);
		BeanUtils.copyProperties(createdUser, returnValue);

		return returnValue;
	}

	@PutMapping
	public String updateUser() {
		return "update user method was called";
	}

	@DeleteMapping
	public String deleteUser() {
		return "delete method was called";
	}
}
