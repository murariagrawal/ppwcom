package com.panipuri.vo;

import java.math.BigDecimal;
import java.util.List;

public class ItemVo {
	private long itemId;
	private String itemName;
	private List<String> itemDetails;
	private BigDecimal itemPrice;
	private int itemQuantity;
	/**
	 * @return the itemId
	 */
	public long getItemId() {
		return itemId;
	}
	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	/**
	 * @return the itemDetails
	 */
	public List<String> getItemDetails() {
		return itemDetails;
	}
	/**
	 * @param itemDetails the itemDetails to set
	 */
	public void setItemDetails(List<String> itemDetails) {
		this.itemDetails = itemDetails;
	}
	/**
	 * @return the itemPrice
	 */
	public BigDecimal getItemPrice() {
		return itemPrice;
	}
	/**
	 * @param itemPrice the itemPrice to set
	 */
	public void setItemPrice(BigDecimal itemPrice) {
		this.itemPrice = itemPrice;
	}
	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}
	/**
	 * @param itemName the itemName to set
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	/**
	 * @return the itemQuantity
	 */
	public int getItemQuantity() {
		return itemQuantity;
	}
	/**
	 * @param itemQuantity the itemQuantity to set
	 */
	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}
	
}
