package com.test.hibernate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.engine.Cascade;

@Entity
@Table(name="order_toppings")
public class OrderToppings {
	@Id
	@Column
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private long orderToppingId;
	@ManyToOne
	@JoinColumn(name="order_id")
	private Order order;
	@ManyToOne()
	@JoinColumn(name="topping_id")
	private AvailableTopping topping;
	@Column
	private int quantity;
	
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
	/**
	 * @return the order
	 */
	public Order getOrder() {
		return order;
	}
	/**
	 * @param order the order to set
	 */
	public void setOrder(Order order) {
		this.order = order;
	}
}
