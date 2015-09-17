package com.panipuri.vo;

import java.math.BigDecimal;

public class ToppingVo {
	private long toppingId;
	private String toppingName;
	private Integer quantity;
	private BigDecimal price;
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
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
}
