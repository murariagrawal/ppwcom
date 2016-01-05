package com.test.hibernate.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.panipuri.vo.AreaVo;
import com.panipuri.vo.CrewVo;
import com.test.hibernate.Crew;
import com.test.hibernate.MasterDeliveryArea;

public class CrewDaoImpl {
	private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }
	
	public void addCrew(String crewName, String contactNumber, String address, long salary, AreaVo areaVo) {
		Session session = this.sessionFactory.getCurrentSession();
		session.beginTransaction();
		Crew crew = new Crew();
		crew.setContactnumber(contactNumber);
		crew.setAddress(crewName);
		crew.setName(crewName);
		crew.setSalary(salary);
		if(areaVo != null) {
			MasterDeliveryArea masterDeliveryArea = (MasterDeliveryArea)session.get(MasterDeliveryArea.class, areaVo.getDeliveryAreaId());
			crew.setArea(masterDeliveryArea);
		}
		
		
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
	public List<CrewVo> getAllCrews() {
		Session session = this.sessionFactory.openSession();
		List<Crew> allAvailableCrews = (List<Crew>) session.createCriteria(Crew.class);
		List<CrewVo> crewList = convertCrewToCrewVo(allAvailableCrews);
		return crewList;
	}
	private List<CrewVo> convertCrewToCrewVo(List<Crew> allAvailableCrews) {
		List<CrewVo> crewVoList = new ArrayList<CrewVo>();
		if(allAvailableCrews != null) {
			CrewVo crewVo = null;
			AreaVo areaVo =null;
			for(Crew crew :allAvailableCrews) {
				areaVo =null;
				crewVo = new CrewVo();
				List<AreaVo> areaVoList = new ArrayList<AreaVo>();
				MasterDeliveryArea area = crew.getArea();
				if(null != area) {
					areaVo = new AreaVo();
					areaVo.setAreaName(area.getAreaName());
					
					areaVo.setDeliveryAreaId(area.getDeliveryAreaId());
					areaVoList.add(areaVo);
					
					crewVo.setArea(areaVoList);
				}
				crewVo.setAddress(crew.getAddress());
				crewVo.setAvailable(crew.isAvailable());
				crewVo.setContactnumber(crew.getContactnumber());
				crewVo.setCrewId(crew.getCrewId());
				crewVo.setName(crew.getName());
				crewVo.setSalary(crew.getSalary());
				crewVoList.add(crewVo);
			}
		}
		return crewVoList;
	}
	public void updateCrewDetails(String crewName,String contactNumber, long salary, String address, String crewId) {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		Crew selectedCrew = (Crew)session.get(Crew.class, new Long(crewId));
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
	public void updateCrewDetailsLocation(String crewId, BigDecimal latitude, BigDecimal longitude) {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		Crew selectedCrew = (Crew)session.get(Crew.class, new Long(crewId));
		if(null != selectedCrew) {
			selectedCrew.setCurrentLatitude(latitude);
			selectedCrew.setCurrentLongitude(longitude);
			session.update(selectedCrew);
			session.getTransaction().commit();
			session.close();
		}
		
	}
	public Crew getCrewDetails(String crewId) {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		Crew selectedCrew = (Crew)session.get(Crew.class, new Long(crewId));
		
			session.getTransaction().commit();
			session.close();
		return selectedCrew;
		
	}
	
	public Crew validateCrewCredential(String userId, String password) {
		Crew selectedCrew = null;
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		List<Crew> selectedCrewlist = (List<Crew>)session.createCriteria(Crew.class).add(Restrictions.eq("userId", userId)).add(Restrictions.eq("password", password)).list();
		if(null != selectedCrewlist && !selectedCrewlist.isEmpty()) {
			selectedCrew = selectedCrewlist.get(0);
		}
			session.getTransaction().commit();
			session.close();
		return selectedCrew;
		
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
