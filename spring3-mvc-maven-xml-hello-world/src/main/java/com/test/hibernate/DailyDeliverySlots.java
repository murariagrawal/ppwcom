package com.test.hibernate;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="daily_delivery_slot")
public class DailyDeliverySlots {
	@Id
	@Column(name="slotId")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long slotId;
	@Column(name="slot_start_time")
	private int slotStartTime;
	@Column(name="slot_end_time")
	private int slotEndTime;
	@Column(name="slot_date")
	private Date slotdate;
	@Column(name="slot_quantity")
	private int slotQuantity;
	@ManyToOne
	@JoinColumn(name="delivery_area_id")
	private MasterDeliveryArea deliveryArea; 
	/**
	 * @return the slotId
	 */
	public long getSlotId() {
		return slotId;
	}
	/**
	 * @param slotId the slotId to set
	 */
	public void setSlotId(long slotId) {
		this.slotId = slotId;
	}
	/**
	 * @return the slotStartTime
	 */
	public int getSlotStartTime() {
		return slotStartTime;
	}
	/**
	 * @param slotStartTime the slotStartTime to set
	 */
	public void setSlotStartTime(int slotStartTime) {
		this.slotStartTime = slotStartTime;
	}
	/**
	 * @return the slotEndTime
	 */
	public int getSlotEndTime() {
		return slotEndTime;
	}
	/**
	 * @param slotEndTime the slotEndTime to set
	 */
	public void setSlotEndTime(int slotEndTime) {
		this.slotEndTime = slotEndTime;
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
	/**
	 * @return the deliveryArea
	 */
	public MasterDeliveryArea getDeliveryArea() {
		return deliveryArea;
	}
	/**
	 * @param deliveryArea the deliveryArea to set
	 */
	public void setDeliveryArea(MasterDeliveryArea deliveryArea) {
		this.deliveryArea = deliveryArea;
	}
	/**
	 * @return the slotdate
	 */
	public Date getSlotdate() {
		return slotdate;
	}
	/**
	 * @param slotdate the slotdate to set
	 */
	public void setSlotdate(Date slotdate) {
		this.slotdate = slotdate;
	}
	

}
