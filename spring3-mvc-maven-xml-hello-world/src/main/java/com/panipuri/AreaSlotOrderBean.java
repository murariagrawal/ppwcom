package com.panipuri;

import java.util.ArrayList;
import java.util.List;

public class AreaSlotOrderBean {
	private String slotTime;
	private String slotQuantity;
	private List<Integer> itemId = new ArrayList<Integer>();
	private List<Integer> itemOrderQuantity = new ArrayList<Integer>();
	/**
	 * @return the slotStartTime
	 */
	public String getSlotTime() {
		return slotTime;
	}
	/**
	 * @param slotStartTime the slotStartTime to set
	 */
	public void setSlotTime(String slotStartTime) {
		this.slotTime = slotStartTime;
	}
	/**
	 * @return the slotQuantity
	 */
	public String getSlotQuantity() {
		return slotQuantity;
	}
	/**
	 * @param slotQuantity the slotQuantity to set
	 */
	public void setSlotQuantity(String slotQuantity) {
		this.slotQuantity = slotQuantity;
	}
	/**
	 * @return the itemId
	 */
	public List<Integer> getItemId() {
		return itemId;
	}
	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(List<Integer> itemId) {
		this.itemId = itemId;
	}
	/**
	 * @return the itemOrderQuantity
	 */
	public List<Integer> getItemOrderQuantity() {
		return itemOrderQuantity;
	}
	/**
	 * @param itemOrderQuantity the itemOrderQuantity to set
	 */
	public void setItemOrderQuantity(List<Integer> itemOrderQuantity) {
		this.itemOrderQuantity = itemOrderQuantity;
	}
	

}
