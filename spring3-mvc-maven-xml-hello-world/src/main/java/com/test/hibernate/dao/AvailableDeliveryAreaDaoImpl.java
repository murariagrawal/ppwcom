package com.test.hibernate.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.test.hibernate.AvailableDeliveryArea;
import com.test.hibernate.Crew;
import com.test.hibernate.MasterDeliveryArea;

public class AvailableDeliveryAreaDaoImpl {
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public void addDeliveryArea(String city, String state, long zipcode, String areaName) {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		MasterDeliveryArea masterDeliveryArea = null;
		List<MasterDeliveryArea> allAvailableDeliveryArea = (List<MasterDeliveryArea>) session
				.createCriteria(MasterDeliveryArea.class).add(Restrictions.eq("", areaName));
		if(null!= allAvailableDeliveryArea && !allAvailableDeliveryArea.isEmpty()) {
			masterDeliveryArea =  allAvailableDeliveryArea.get(0);
			
		}
		AvailableDeliveryArea deliveryArea = new AvailableDeliveryArea();
		deliveryArea.setCity(city);
		deliveryArea.setState(state);
		deliveryArea.setZipcode(zipcode);
		deliveryArea.setArea(masterDeliveryArea);
		session.save(deliveryArea);
		session.getTransaction().commit();
		session.close();
	}

	public void addMasterDeliveryArea(String areaName) {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		MasterDeliveryArea deliveryArea = new MasterDeliveryArea();
		deliveryArea.setAreaName(areaName);
		session.save(deliveryArea);
		session.getTransaction().commit();
		session.close();
	}

	@SuppressWarnings("unchecked")
	public MasterDeliveryArea getMasterDeliveryArea(String areaName) {
		Session session = this.sessionFactory.openSession();

		List<MasterDeliveryArea> allAvailableDeliveryArea = (List<MasterDeliveryArea>) session
				.createCriteria(MasterDeliveryArea.class).add(Restrictions.eq("", areaName));
		if(null!= allAvailableDeliveryArea && !allAvailableDeliveryArea.isEmpty()) {
			return allAvailableDeliveryArea.get(0);
			
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<AvailableDeliveryArea> getAllAvailableDeliveryArea() {
		Session session = this.sessionFactory.openSession();

		List<AvailableDeliveryArea> allAvailableDeliveryArea = (List<AvailableDeliveryArea>) session
				.createCriteria(AvailableDeliveryArea.class);
		return allAvailableDeliveryArea;
	}

	@SuppressWarnings("unchecked")
	public List<AvailableDeliveryArea> isInAvaialbleDeliveryArea(long zipcode) {
		Session session = this.sessionFactory.openSession();
		List<AvailableDeliveryArea> allAvailableCrews = (List<AvailableDeliveryArea>) session
				.createCriteria(AvailableDeliveryArea.class).add(Restrictions.eq("zipcode", zipcode));
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
