package com.test.hibernate.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.test.hibernate.Crew;

public class CrewDaoImpl {
	private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }
	
	public void addCrew(String crewName, String contactNumber, String address, long salary) {
		Session session = this.sessionFactory.getCurrentSession();
		session.beginTransaction();
		Crew crew = new Crew();
		crew.setContactnumber(contactNumber);
		crew.setAddress(crewName);
		crew.setName(crewName);
		crew.setSalary(salary);
		session.save(crew);
		session.getTransaction().commit();
		session.close();
	}
	
	@SuppressWarnings("unchecked")
	public List<Crew> getAllAvailableCrews() {
		Session session = this.sessionFactory.openSession();
		
		List<Crew> allAvailableCrews = (List<Crew>)session.createCriteria(Crew.class).add(Restrictions.eq("isAvailable", true));
		return allAvailableCrews;
	}
	@SuppressWarnings("unchecked")
	public List<Crew> getAllCrews() {
		Session session = this.sessionFactory.openSession();
		List<Crew> allAvailableCrews = (List<Crew>) session.createCriteria(Crew.class);
		return allAvailableCrews;
	}
	public void updateCrewDetails(String crewName,String contactNumber, long salary, String address, long crewId) {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		Crew selectedCrew = (Crew)session.get(Crew.class, crewId);
		if(null != selectedCrew) {
			if(null != crewName && !crewName.trim().equals("")) {
				selectedCrew.setName(crewName);
			} 
			selectedCrew.setSalary(salary);
			if(null != contactNumber && !contactNumber.trim().equals("")) {
				selectedCrew.setContactnumber(contactNumber);
			}
			if(null != address && !address.trim().equals("")) {
				selectedCrew.setAddress(address);
			}
			session.update(selectedCrew);
			session.getTransaction().commit();
			session.close();
		}
		
	}
	
	public void deleteCrew(long crewId) {
		Session session = this.sessionFactory.getCurrentSession();
		session.beginTransaction();
		Crew selectedCrew = (Crew)session.get(Crew.class, crewId);
		session.delete(selectedCrew);
		session.getTransaction().commit();
		session.close();
	}
}
