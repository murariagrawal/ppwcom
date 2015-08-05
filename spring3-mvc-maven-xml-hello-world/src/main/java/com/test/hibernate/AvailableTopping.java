package com.test.hibernate;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="available_topping")
public class AvailableTopping {
	@Id
	@Column(name="topping_id")
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private long toppingId;
	@Column(name="topping_name")
	private String toppingName;
	@Column(name="topping_price")
	private BigDecimal toppingPrice;
	/**
	 * @return the toppingId
	 */
	public long getToppingId() {
		return toppingId;
	}
	/**
	 * @param toppingId the toppingId to set
	 */
	public void setToppingId(long toppingId) {
		this.toppingId = toppingId;
	}
	/**
	 * @return the toppingName
	 */
	public String getToppingName() {
		return toppingName;
	}
	/**
	 * @param toppingName the toppingName to set
	 */
	public void setToppingName(String toppingName) {
		this.toppingName = toppingName;
	}
	/**
	 * @return the toppingPrice
	 */
	public BigDecimal getToppingPrice() {
		return toppingPrice;
	}
	/**
	 * @param toppingPrice the toppingPrice to set
	 */
	public void setToppingPrice(BigDecimal toppingPrice) {
		this.toppingPrice = toppingPrice;
	}
	
}
