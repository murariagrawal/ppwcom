package com.panipuri.vo;

import java.math.BigDecimal;
import java.util.List;

import com.test.hibernate.PartyItemQuantity;

public class ItemVo {
	private long itemId;
	private String itemName;
	private List<String> itemDetails;
	private BigDecimal itemPrice;
	private int itemQuantity;
	private boolean partyItem;
	private List<PartyItemQuantity> partyQuantitylist;
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
	/**
	 * @return the partyItem
	 */
	public boolean isPartyItem() {
		return partyItem;
	}
	/**
	 * @param partyItem the partyItem to set
	 */
	public void setPartyItem(boolean partyItem) {
		this.partyItem = partyItem;
	}
	/**
	 * @return the partyQuantitylist
	 */
	public List<PartyItemQuantity> getPartyQuantitylist() {
		return partyQuantitylist;
	}
	/**
	 * @param partyQuantitylist the partyQuantitylist to set
	 */
	public void setPartyQuantitylist(List<PartyItemQuantity> partyQuantitylist) {
		this.partyQuantitylist = partyQuantitylist;
	}
	
}
