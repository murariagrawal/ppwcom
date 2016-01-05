package com.test.hibernate.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.test.hibernate.DeliveryArea;
import com.test.hibernate.DeliverySlot;
import com.test.hibernate.DeliverySlotStock;
import com.test.hibernate.MasterDeliveryArea;

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
		deliverySlot.setTodaySlotQuantity(slotQuantity);
		session.save(deliverySlot);
		session.getTransaction().commit();
		session.close();
	}
	
	public List<DeliverySlot> getAvailableDeliverySlots(String areaId) throws Exception {
		Session session = this.sessionFactory.openSession();
		
		List<DeliverySlot> deliverySlots= null;
		DeliveryArea area = (DeliveryArea) session.get(DeliveryArea.class, new Long(areaId));
		if(null != area) {
			
			Hibernate.initialize(area.getMasterArea().getDeliverySlots());
			deliverySlots = area.getMasterArea().getDeliverySlots();
		} else {
			Exception e = new Exception("ERR_NO_AREA");
			throw e;
		}
		
		session.close();
		return deliverySlots;
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
	public void resetSlotQuantity() {
		Session session = this.sessionFactory.getCurrentSession();
		session.beginTransaction();
		List<DeliverySlot> deliverySlotList = (List<DeliverySlot>)session.createCriteria(DeliverySlot.class).list();
		if(deliverySlotList !=null) {
			MasterDeliveryArea area = null;
			for(DeliverySlot slot:deliverySlotList) {
				area = slot.getDeliveryArea();
				
				if(area.getDeliveryStockList() != null) {
					for(DeliverySlotStock areaStock:area.getDeliveryStockList()) {
						for(DeliverySlotStock slotStock: slot.getDeliveryStockList()) {
							if(areaStock.getId() == slotStock.getId() && slotStock.isStuffing()==areaStock.isStuffing()) {
								slotStock.setQuantity(areaStock.getQuantity());
								slotStock.setInitialQuantity(areaStock.getQuantity());
								slotStock.setQuantityOrdered(0);
							}
						}
					}
				}
				
				slot.setTodaySlotQuantity(slot.getSlotQuantity());
			}
		}
		session.getTransaction().commit();
		session.close();
		
	}
}
