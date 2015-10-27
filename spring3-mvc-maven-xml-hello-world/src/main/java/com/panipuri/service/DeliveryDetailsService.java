package com.panipuri.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.test.hibernate.DailyDeliverySlots;
import com.test.hibernate.dao.DeliverySlotDaoImpl;

@Component
public class DeliveryDetailsService {
	@Autowired DeliverySlotDaoImpl deliverySlotDaoImpl;
	public DailyDeliverySlots fetchDeliverySlots(String zipcode) {
		deliverySlotDaoImpl.getAvailableDeliverySlots(zipcode);
		return null;
	}
	/**
	 * @param deliverySlotDaoImpl the deliverySlotDaoImpl to set
	 */
	public void setDeliverySlotDaoImpl(DeliverySlotDaoImpl deliverySlotDaoImpl) {
		this.deliverySlotDaoImpl = deliverySlotDaoImpl;
	}
}
