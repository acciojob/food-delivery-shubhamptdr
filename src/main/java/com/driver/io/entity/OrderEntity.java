package com.driver.io.entity;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity(name = "orders")
public class OrderEntity {

	@Id
	@GeneratedValue
	private long id;

	@Column(nullable = false)
	private String orderId;

	@Column(nullable = false)
	private float cost;

	@Column(nullable = false)
	private String[] items;

	@Column(nullable = false)
	private String userId;
	
	@Column(nullable = false)
	private boolean status;

	//child wrt to userEntity
	@ManyToOne
	@JoinColumn
	private UserEntity userEntity;

	// parent wrt to foodEntity
	@OneToMany(mappedBy = "orderEntity",cascade = CascadeType.ALL)
	private List<FoodEntity> listOfFood = new ArrayList<>();

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public OrderEntity() {
	}

	public List<FoodEntity> getListOfFood() {
		return listOfFood;
	}

	public void setListOfFood(List<FoodEntity> listOfFood) {
		this.listOfFood = listOfFood;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public String[] getItems() {
		return items;
	}

	public void setItems(String[] items) {
		this.items = items;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}
