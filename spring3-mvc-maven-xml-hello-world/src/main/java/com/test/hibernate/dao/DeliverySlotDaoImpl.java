package com.test.hibernate.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.test.hibernate.AvailableZipcodes;
import com.test.hibernate.DeliverySlot;

public class DeliverySlotDaoImpl {
	
	private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }
	public void addDeliverySlot(String startTime, String endTime, int slotQuantity) {
		Session session = this.sessionFactory.getCurrentSession();
		session.beginTransaction();
		DeliverySlot deliverySlot = new DeliverySlot();
		deliverySlot.setEndTime(endTime);
		deliverySlot.setStartTime(startTime);
		deliverySlot.setSlotQuantity(slotQuantity);
		session.save(deliverySlot);
		session.getTransaction().commit();
		session.close();
	}
	
	public List<AvailableZipcodes> getAvailableDeliverySlots(String zipcode) {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		List<AvailableZipcodes> zipcodes = (List<AvailableZipcodes>) session.createCriteria(AvailableZipcodes.class, "zipcodes")													
													.add(Restrictions.eq("zipcodes.zipcode", new Long(zipcode))).list();
		if(null != zipcodes && !zipcodes.isEmpty()) {
			zipcodes.get(0).getArea().getDeliverySlots();
		}
		session.getTransaction().commit();
		session.close();
		return zipcodes;
	}
	public void updateDeliverySlot(String startTime, int slotQuantity, String endTime, long deliverySlotId) {
		Session session = this.sessionFactory.getCurrentSession();
		session.beginTransaction();
		DeliverySlot selectedDeliverySlot = (DeliverySlot)session.get(DeliverySlot.class, deliverySlotId);
		if(null != selectedDeliverySlot) {
			if(null != startTime && !startTime.trim().equals("")) {
				selectedDeliverySlot.setStartTime(startTime);
			} 
			selectedDeliverySlot.setSlotQuantity(slotQuantity);
			if(null != endTime && !endTime.trim().equals("")) {
				selectedDeliverySlot.setEndTime(endTime);
			}
			session.update(selectedDeliverySlot);
		}
		session.getTransaction().commit();
		session.close();
	}
	
	public void deleteDeliverySlot(long deliverySlotId) {
		Session session = this.sessionFactory.getCurrentSession();
		session.beginTransaction();
		DeliverySlot selectedDeliverySlot = (DeliverySlot)session.get(DeliverySlot.class, deliverySlotId);
		session.delete(selectedDeliverySlot);
		session.getTransaction().commit();
		session.close();
		
	}
}