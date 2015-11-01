package com.panipuri.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

public class DeliverySlotVo {
	private long deliverySlotId;
	private String startTime;
	private String endTime;
	private int slotQuantity;
	/**
	 * @return the deliverySlotId
	 */
	public long getDeliverySlotId() {
		return deliverySlotId;
	}
	/**
	 * @param deliverySlotId the deliverySlotId to set
	 */
	public void setDeliverySlotId(long deliverySlotId) {
		this.deliverySlotId = deliverySlotId;
	}
	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	/**
	 * @return the slotQuantity
	 */
	public int getSlotQuantity() {
		return slotQuantity;
	}
	/**
	 * @param slotQuantity the slotQuantity to set
	 */
	public void setSlotQuantity(int slotQuantity) {
		this.slotQuantity = slotQuantity;
	}
	

}
