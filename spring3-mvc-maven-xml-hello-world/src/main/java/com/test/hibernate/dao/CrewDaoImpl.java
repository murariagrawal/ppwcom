package com.test.hibernate.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.test.hibernate.Crew;

public class CrewDaoImpl extends HibernateDaoSupport {
	
	
	public void addCrew(String crewName, String contactNumber, String address, long salary) {
		Crew crew = new Crew();
		crew.setContactnumber(contactNumber);
		crew.setAddress(crewName);
		crew.setName(crewName);
		crew.setSalary(salary);
		getHibernateTemplate().save(crew);
	}
	
	@SuppressWarnings("unchecked")
	public List<Crew> getAllAvailableCrews() {
		DetachedCriteria allAvailableCrewCriteria = DetachedCriteria.forClass(Crew.class).add(Restrictions.eq("isAvailable", true));
		List<Crew> allAvailableCrews = getHibernateTemplate().findByCriteria(allAvailableCrewCriteria);
		return allAvailableCrews;
	}
	@SuppressWarnings("unchecked")
	public List<Crew> getAllCrews() {
		DetachedCriteria allAvailableCrewCriteria = DetachedCriteria.forClass(Crew.class);
		List<Crew> allAvailableCrews = getHibernateTemplate().findByCriteria(allAvailableCrewCriteria);
		return allAvailableCrews;
	}
	public void updateCrewDetails(String crewName,String contactNumber, long salary, String address, long crewId) {
		Crew selectedCrew = (Crew)getHibernateTemplate().get(Crew.class, crewId);
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
			getHibernateTemplate().update(selectedCrew);
		}
		
	}
	
	public void deleteCrew(long crewId) {
		Crew selectedCrew = (Crew)getHibernateTemplate().get(Crew.class, crewId);
		getHibernateTemplate().delete(selectedCrew);
		
	}
}
