package com.coodru.mobile.app.ws.io.entity;

import com.coodru.mobile.app.ws.shared.dto.UserDto;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Entity(name = "addresses")
public class AddressEntity implements Serializable {

	@Serial private static final long serialVersionUID = -5500813463077386006L;

	@Id
	@GeneratedValue
	private long id;

	@Column(length = 30, nullable = false)	// it means that this propriety will be required when creating an entry in DB
	private String addressId;

	@Column(length = 15, nullable = false)
	private String city;

	@Column(length = 15, nullable = false)
	private String country;

	@Column(length = 100, nullable = false)
	private String streetName;

	@Column(length = 7, nullable = false)
	private String postalCode;

	@Column(length = 10, nullable = false)
	private String type;

	@ManyToOne
	@JoinColumn(name = "users_id")	// this is the name of the FK of users table
	private UserDto userDetails;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public UserDto getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDto userDetails) {
		this.userDetails = userDetails;
	}
}
