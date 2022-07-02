package com.coodru.mobile.app.ws.shared;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class Utils {
	private final Random RANDOM = new SecureRandom();
	private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
//	private final int ITERATIONS = 10000;
//	private final int KEY_LENGTH = 256;

	public String generateId (int length) {
		return generateRandomString(length);
	}

	private String generateRandomString(int length) {
		StringBuilder builder = new StringBuilder(length);

		for (int i = 0; i < length; i++) {
			builder.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
		}
		return builder.toString();
	}

}
