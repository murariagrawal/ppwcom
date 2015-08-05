package com.test.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="delivery_slot")
public class DeliverySlot {
	@Id
	@Column(name="delivery_slot_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long deliverySlotId;
	@Column(nullable=false)
	private String startTime;
	@Column(nullable=false)
	private String endTime;
	@Column(nullable=false)
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
