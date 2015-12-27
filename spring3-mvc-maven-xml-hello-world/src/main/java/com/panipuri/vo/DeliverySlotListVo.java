package com.panipuri.vo;

import java.util.List;

public class DeliverySlotListVo {
	private List<DeliverySlotVo> availableSlot;
	private List<DeliverySlotVo> fullSlot;
	/**
	 * @return the availableSlot
	 */
	public List<DeliverySlotVo> getAvailableSlot() {
		return availableSlot;
	}
	/**
	 * @param availableSlot the availableSlot to set
	 */
	public void setAvailableSlot(List<DeliverySlotVo> availableSlot) {
		this.availableSlot = availableSlot;
	}
	/**
	 * @return the fullSlot
	 */
	public List<DeliverySlotVo> getFullSlot() {
		return fullSlot;
	}
	/**
	 * @param fullSlot the fullSlot to set
	 */
	public void setFullSlot(List<DeliverySlotVo> fullSlot) {
		this.fullSlot = fullSlot;
	}

}
