package com.test.hibernate.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.test.hibernate.AvailableZipcodes;
import com.test.hibernate.Crew;
import com.test.hibernate.DeliverySlot;
import com.test.hibernate.MasterDeliveryArea;

public class AvailableDeliveryAreaDaoImpl {
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public void addZipcodeToDeliveryArea(List<Long> zipcodes, String areaName) {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		MasterDeliveryArea masterDeliveryArea = null;
		List<MasterDeliveryArea> allAvailableDeliveryArea = (List<MasterDeliveryArea>) session
				.createCriteria(MasterDeliveryArea.class).add(Restrictions.eq("areaName", areaName));
		if(null!= allAvailableDeliveryArea && !allAvailableDeliveryArea.isEmpty()) {
			masterDeliveryArea =  allAvailableDeliveryArea.get(0);
			
		}
		List<AvailableZipcodes> areaZipcodes = new ArrayList<AvailableZipcodes>();
		if(null != zipcodes) {
			for(Long zipcode:zipcodes) {
				AvailableZipcodes deliveryArea = new AvailableZipcodes();
				deliveryArea.setZipcode(zipcode);
				areaZipcodes.add(deliveryArea);
				
			}
		}
		if(null == masterDeliveryArea.getZipcodes()) {
			masterDeliveryArea.setZipcodes(areaZipcodes);
		} else {
			masterDeliveryArea.getZipcodes().addAll(areaZipcodes);
		}
		session.update(masterDeliveryArea);
		session.getTransaction().commit();
		session.close();
	}

	public void addMasterDeliveryArea(String areaName,String city, String state, List<Long> zipcodes,  List<DeliverySlot> deliverySlots) {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		MasterDeliveryArea masterDeliveryArea = new MasterDeliveryArea();
		masterDeliveryArea.setAreaName(areaName);
		List<AvailableZipcodes> areaZipcodes = new ArrayList<AvailableZipcodes>();
		if(null != zipcodes) {
			for(Long zipcode:zipcodes) {
				AvailableZipcodes deliveryArea = new AvailableZipcodes();
				deliveryArea.setZipcode(zipcode);
				deliveryArea.setArea(masterDeliveryArea);
				areaZipcodes.add(deliveryArea);
				
			}
		}
		if(null != deliverySlots) {
			for(DeliverySlot deliverySlot:deliverySlots) {
				
				deliverySlot.setDeliveryArea(masterDeliveryArea);
				
			}
		}
		masterDeliveryArea.setCity(city);
		masterDeliveryArea.setState(state);
		masterDeliveryArea.setDeliverySlots(deliverySlots);
		masterDeliveryArea.setZipcodes(areaZipcodes);
		session.save(masterDeliveryArea);
		session.getTransaction().commit();
		session.close();
	}

	@SuppressWarnings("unchecked")
	public MasterDeliveryArea getMasterDeliveryArea(String areaName) {
		Session session = this.sessionFactory.openSession();

		List<MasterDeliveryArea> allAvailableDeliveryArea = (List<MasterDeliveryArea>) session
				.createCriteria(MasterDeliveryArea.class).add(Restrictions.eq("areaName", areaName));
		if(null!= allAvailableDeliveryArea && !allAvailableDeliveryArea.isEmpty()) {
			return allAvailableDeliveryArea.get(0);
			
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<MasterDeliveryArea> getAllAvailableDeliveryArea() {
		Session session = this.sessionFactory.openSession();

		List<MasterDeliveryArea> allAvailableDeliveryArea = (List<MasterDeliveryArea>) session
				.createCriteria(MasterDeliveryArea.class);
		return allAvailableDeliveryArea;
	}

	@SuppressWarnings("unchecked")
	public List<AvailableZipcodes> isInAvaialbleDeliveryArea(long zipcode) {
		Session session = this.sessionFactory.openSession();
		List<AvailableZipcodes> allAvailableCrews = (List<AvailableZipcodes>) session
				.createCriteria(AvailableZipcodes.class).add(Restrictions.eq("zipcode", zipcode));
		return allAvailableCrews;
	}

	public void updateCrewDetails(String crewName, String contactNumber, long salary, String address, long crewId) {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		Crew selectedCrew = (Crew) session.get(Crew.class, crewId);
		if (null != selectedCrew) {
			if (null != crewName && !crewName.trim().equals("")) {
				selectedCrew.setName(crewName);
			}
			selectedCrew.setSalary(salary);
			if (null != contactNumber && !contactNumber.trim().equals("")) {
				selectedCrew.setContactnumber(contactNumber);
			}
			if (null != address && !address.trim().equals("")) {
				selectedCrew.setAddress(address);
			}
			session.update(selectedCrew);
			session.getTransaction().commit();
			session.close();
		}

	}

	public void deleteMasterDeliveryArea(long areaID) {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		MasterDeliveryArea selectedArea = (MasterDeliveryArea) session.get(MasterDeliveryArea.class, areaID);
		session.delete(selectedArea);
		session.getTransaction().commit();
		session.close();
	}
}
