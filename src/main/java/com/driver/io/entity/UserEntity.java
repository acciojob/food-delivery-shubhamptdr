package com.driver.io.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity(name = "users")
public class UserEntity{

	@Id
	@GeneratedValue
	private long id;

	@Column(nullable = false)
	private String userId;

	@Column(nullable = false, length = 20)
	private String firstName;

	@Column(nullable = false, length = 50)
	private String lastName;

	@Column(nullable = false, length = 120, unique = true)
	private String email;

	//parent wrt to orderEntity
	@OneToMany(mappedBy = "userEntity",cascade = CascadeType.ALL)
	List<OrderEntity> listOfOrders;

	public List<OrderEntity> getListOfOrders() {
		return listOfOrders;
	}

	public void setListOfOrders(List<OrderEntity> listOfOrders) {
		this.listOfOrders = listOfOrders;
	}

	public UserEntity() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

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

}
