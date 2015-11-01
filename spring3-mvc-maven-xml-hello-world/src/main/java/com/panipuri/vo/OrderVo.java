package com.panipuri.vo;

import java.math.BigDecimal;
import java.util.List;

public class OrderVo {
	private long orderId;
	private List<ItemVo> itemList;
	private List<ToppingVo> toppingList;
	private Float totalPrice;
	private AddressVo deliveryAddress;
	private String deliverySlot;
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
	 * @return the itemList
	 */
	public List<ItemVo> getItemList() {
		return itemList;
	}
	/**
	 * @param itemList the itemList to set
	 */
	public void setItemList(List<ItemVo> itemList) {
		this.itemList = itemList;
	}
	/**
	 * @return the toppingList
	 */
	public List<ToppingVo> getToppingList() {
		return toppingList;
	}
	/**
	 * @param toppingList the toppingList to set
	 */
	public void setToppingList(List<ToppingVo> toppingList) {
		this.toppingList = toppingList;
	}
	/**
	 * @return the totalPrice
	 */
	public Float getTotalPrice() {
		return totalPrice;
	}
	/**
	 * @param totalPrice the totalPrice to set
	 */
	public void setTotalPrice(Float totalPrice) {
		this.totalPrice = totalPrice;
	}
	/**
	 * @return the deliveryAddress
	 */
	public AddressVo getDeliveryAddress() {
		return deliveryAddress;
	}
	/**
	 * @param deliveryAddress the deliveryAddress to set
	 */
	public void setDeliveryAddress(AddressVo deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	/**
	 * @return the deliverySlot
	 */
	public String getDeliverySlot() {
		return deliverySlot;
	}
	/**
	 * @param deliverySlot the deliverySlot to set
	 */
	public void setDeliverySlot(String deliverySlot) {
		this.deliverySlot = deliverySlot;
	}
}
