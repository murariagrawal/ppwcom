package com.test.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="order_items")

public class OrderItems {
	@Id
	@Column
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private long orderItemId;
	@ManyToOne
	@JoinColumn(name="order_id")
	
	private Order order;
	@ManyToOne
	@JoinColumn(name="item_id")
	private Item item;
	@Column
	private int quantity;
	
	
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
	 * @return the item
	 */
	public Item getItem() {
		return item;
	}
	/**
	 * @param item the item to set
	 */
	public void setItem(Item item) {
		this.item = item;
	}
	/**
	 * @return the orderItemId
	 */
	public long getOrderItemId() {
		return orderItemId;
	}
	/**
	 * @param orderItemId the orderItemId to set
	 */
	public void setOrderItemId(long orderItemId) {
		this.orderItemId = orderItemId;
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
