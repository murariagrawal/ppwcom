package com.test.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "delivery_slot_stock")
public class DeliverySlotStock {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long deliveryStockId;
	@Column
	private long id;
	@Column
	private int quantity;
	@Column
	private boolean stuffing;
	@ManyToOne
	@JoinColumn(name = "delivery_slot_id")
	private DeliverySlot slot;
	@ManyToOne
	@JoinColumn(name = "delivery_area_id")
	private MasterDeliveryArea area;
	/**
	 * @return the deliveryStockId
	 */
	public long getDeliveryStockId() {
		return deliveryStockId;
	}
	/**
	 * @param deliveryStockId the deliveryStockId to set
	 */
	public void setDeliveryStockId(long deliveryStockId) {
		this.deliveryStockId = deliveryStockId;
	}
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return the stuffing
	 */
	public boolean isStuffing() {
		return stuffing;
	}
	/**
	 * @param stuffing the stuffing to set
	 */
	public void setStuffing(boolean stuffing) {
		this.stuffing = stuffing;
	}
	
	/**
	 * @return the slot
	 */
	public DeliverySlot getSlot() {
		return slot;
	}
	/**
	 * @param slot the slot to set
	 */
	public void setSlot(DeliverySlot slot) {
		this.slot = slot;
	}
	/**
	 * @return the area
	 */
	public MasterDeliveryArea getArea() {
		return area;
	}
	/**
	 * @param area the area to set
	 */
	public void setArea(MasterDeliveryArea area) {
		this.area = area;
	}
}
