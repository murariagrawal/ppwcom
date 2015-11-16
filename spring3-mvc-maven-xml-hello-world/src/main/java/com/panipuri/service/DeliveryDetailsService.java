package com.panipuri.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.panipuri.vo.DeliverySlotVo;
import com.test.hibernate.DeliverySlot;
import com.test.hibernate.dao.DeliverySlotDaoImpl;

@Component
public class DeliveryDetailsService {
	@Autowired DeliverySlotDaoImpl deliverySlotDaoImpl;
	public List<DeliverySlotVo> fetchDeliverySlots(String zipcode) {
		List<DeliverySlotVo> deliverySlotsList = new ArrayList<DeliverySlotVo>();
		List<DeliverySlot> deliverySlots = deliverySlotDaoImpl.getAvailableDeliverySlots(zipcode);
		if(null !=deliverySlots) {
			Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("IST"));
			DeliverySlotVo deliverySlotVo = null;
			String startTime =null;
			String deliverySlotTime = null;
			for(DeliverySlot deliverySlot : deliverySlots) {				
				
				int hours = calendar.get(Calendar.HOUR_OF_DAY);
				int minutes = calendar.get(Calendar.MINUTE);
				startTime = deliverySlot.getStartTime();
				deliverySlotTime = startTime.substring(0, startTime.indexOf("PM"));
				int slotTime = Integer.parseInt(deliverySlotTime)+12;
				if(hours < slotTime && minutes<50 && deliverySlot.getTodaySlotQuantity()>0) {
					deliverySlotVo = new DeliverySlotVo();
					deliverySlotVo.setDeliverySlotId(deliverySlot.getDeliverySlotId());
					deliverySlotVo.setEndTime(deliverySlot.getEndTime());
					deliverySlotVo.setStartTime(deliverySlot.getStartTime());
					deliverySlotVo.setSlotQuantity(deliverySlot.getTodaySlotQuantity());
					deliverySlotsList.add(deliverySlotVo);
				}
			}
		}
 		return deliverySlotsList;
	}
	/**
	 * @param deliverySlotDaoImpl the deliverySlotDaoImpl to set
	 */
	public void setDeliverySlotDaoImpl(DeliverySlotDaoImpl deliverySlotDaoImpl) {
		this.deliverySlotDaoImpl = deliverySlotDaoImpl;
	}
}
