package com.test.hibernate.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.test.hibernate.DeliverySlot;

public class DeliverySlotDaoImpl extends HibernateDaoSupport{
	public void addDeliverySlot(String startTime, String endTime, int slotQuantity) {
		DeliverySlot deliverySlot = new DeliverySlot();
		deliverySlot.setEndTime(endTime);
		deliverySlot.setStartTime(startTime);
		deliverySlot.setSlotQuantity(slotQuantity);
		getHibernateTemplate().save(deliverySlot);
	}
	
	public List<DeliverySlot> getAvailableDeliverySlots() {
		DetachedCriteria fetAvailableDeliverySlots = DetachedCriteria.forClass(DeliverySlot.class);
		@SuppressWarnings("unchecked")
		List<DeliverySlot> deliverySlots = (List<DeliverySlot>) getHibernateTemplate().findByCriteria(fetAvailableDeliverySlots);
		return deliverySlots;
	}
	public void updateDeliverySlot(String startTime, int slotQuantity, String endTime, long deliverySlotId) {
		DeliverySlot selectedDeliverySlot = (DeliverySlot)getHibernateTemplate().get(DeliverySlot.class, deliverySlotId);
		if(null != selectedDeliverySlot) {
			if(null != startTime && !startTime.trim().equals("")) {
				selectedDeliverySlot.setStartTime(startTime);
			} 
			selectedDeliverySlot.setSlotQuantity(slotQuantity);
			if(null != endTime && !endTime.trim().equals("")) {
				selectedDeliverySlot.setEndTime(endTime);
			}
			getHibernateTemplate().update(selectedDeliverySlot);
		}
		
	}
	
	public void deleteDeliverySlot(long deliverySlotId) {
		DeliverySlot selectedDeliverySlot = (DeliverySlot)getHibernateTemplate().get(DeliverySlot.class, deliverySlotId);
		getHibernateTemplate().delete(selectedDeliverySlot);
		
	}
}
