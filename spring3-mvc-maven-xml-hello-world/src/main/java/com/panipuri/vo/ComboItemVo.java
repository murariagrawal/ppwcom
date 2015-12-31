package com.panipuri.vo;

import java.util.List;

public class ComboItemVo {
	private List<String> itemIds;
	private List<String> quantity;
	private String comboItemId;
	/**
	 * @return the itemIds
	 */
	public List<String> getItemIds() {
		return itemIds;
	}
	/**
	 * @param itemIds the itemIds to set
	 */
	public void setItemIds(List<String> itemIds) {
		this.itemIds = itemIds;
	}
	/**
	 * @return the quantity
	 */
	public List<String> getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(List<String> quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return the comboItemId
	 */
	public String getComboItemId() {
		return comboItemId;
	}
	/**
	 * @param comboItemId the comboItemId to set
	 */
	public void setComboItemId(String comboItemId) {
		this.comboItemId = comboItemId;
	}

}
