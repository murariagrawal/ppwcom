package com.test.hibernate;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="delivery_slot")
public class DeliverySlot {
	@Id
	@Column(name="delivery_slot_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long deliverySlotId;
	@Column
	private String startTime;
	@Column
	private String endTime;
	@Column
	private int slotQuantity;
	@Column
	private int todaySlotQuantity;
	@OneToMany(fetch = FetchType.EAGER,cascade= CascadeType.ALL, mappedBy="slot")
	private List<DeliverySlotStock> deliveryStockList;
	
	@ManyToOne(targetEntity=MasterDeliveryArea.class)
	@JoinColumn(name="delivery_area_id")
	private MasterDeliveryArea deliveryArea; 
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
	 * @return the todaySlotQuantity
	 */
	public int getTodaySlotQuantity() {
		return todaySlotQuantity;
	}
	/**
	 * @param todaySlotQuantity the todaySlotQuantity to set
	 */
	public void setTodaySlotQuantity(int todaySlotQuantity) {
		this.todaySlotQuantity = todaySlotQuantity;
	}
	/**
	 * @return the deliveryStockList
	 */
	public List<DeliverySlotStock> getDeliveryStockList() {
		return deliveryStockList;
	}
	/**
	 * @param deliveryStockList the deliveryStockList to set
	 */
	public void setDeliveryStockList(List<DeliverySlotStock> deliveryStockList) {
		this.deliveryStockList = deliveryStockList;
	}
	

}
