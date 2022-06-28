package com.coodru.mobile.app.ws.exceptions;

import java.io.Serial;

public class UserServiceException extends RuntimeException {
	@Serial private static final long serialVersionUID = 1708696283730274736L;

	public UserServiceException(String message) {
		super(message);
	}
}
