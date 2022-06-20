package com.coodru.mobile.app.ws.ui.controller.model.request;

import java.util.Objects;

public class UserDetailsRequestModel {

	private String firstName;

	private String lastName;

	private String email;

	private String password;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsRequestModel that = (UserDetailsRequestModel) o;
		return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(
				email, that.email) && Objects.equals(password, that.password);
	}

	@Override public int hashCode() {
		return Objects.hash(firstName, lastName, email, password);
	}

	@Override public String toString() {
		StringBuilder builder = new StringBuilder("UserDetailsRequestModel{");
		builder.append("firstName='").append(firstName).append('\'');
		builder.append(", lastName='").append(lastName).append('\'');
		builder.append(", email='").append(email).append('\'');
		builder.append(", password='").append(password).append('\'');
		builder.append('}');
		return builder.toString();
	}
}
