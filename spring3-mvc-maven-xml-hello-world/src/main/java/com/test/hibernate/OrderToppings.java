package com.test.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="order_toppings")
public class OrderToppings {
	@Id
	@Column
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private long orderToppingId;
	@Column(name="order_id")
	private long orderId;
	@OneToOne(targetEntity=AvailableTopping.class)
	private AvailableTopping topping;
	@Column
	private int quantity;
	/**
	 * @return the orderId
	 */
	public long getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	/**
	 * @return the topping
	 */
	public AvailableTopping getTopping() {
		return topping;
	}
	/**
	 * @param topping the topping to set
	 */
	public void setTopping(AvailableTopping topping) {
		this.topping = topping;
	}
	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return the orderToppingId
	 */
	public long getOrderToppingId() {
		return orderToppingId;
	}
	/**
	 * @param orderToppingId the orderToppingId to set
	 */
	public void setOrderToppingId(long orderToppingId) {
		this.orderToppingId = orderToppingId;
	}
}
