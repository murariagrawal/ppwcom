package com.test.hibernate.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.panipuri.vo.AreaSubAreaVo;
import com.panipuri.vo.AreaVo;
import com.test.hibernate.AvailableZipcodes;
import com.test.hibernate.Crew;
import com.test.hibernate.DeliveryArea;
import com.test.hibernate.DeliverySlot;
import com.test.hibernate.DeliverySlotStock;
import com.test.hibernate.MasterDeliveryArea;

public class AvailableDeliveryAreaDaoImpl {
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	/*public void addZipcodeToDeliveryArea(List<Long> zipcodes, String areaName) {
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
	}*/

	public void addMasterDeliveryArea(String areaName,String city, String state,  List<DeliverySlot> deliverySlots,  List<DeliverySlotStock> stockInfo) {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		MasterDeliveryArea masterDeliveryArea = new MasterDeliveryArea();
		masterDeliveryArea.setAreaName(areaName);
		
		
		masterDeliveryArea.setCity(city);
		masterDeliveryArea.setState(state);
		masterDeliveryArea.setDeliveryStockList(stockInfo);
		for(DeliverySlotStock stock :stockInfo) {
			stock.setArea(masterDeliveryArea);
		}
		session.save(masterDeliveryArea);
		if(null != deliverySlots) {
			for(DeliverySlot deliverySlot:deliverySlots) {				
				deliverySlot.setDeliveryArea(masterDeliveryArea);
				
				session.save(deliverySlot);
			}
		}
		
		masterDeliveryArea.setDeliverySlots(deliverySlots);	
		session.getTransaction().commit();
		session.close();
	}
	public void addDeliveryArea(String areaName,String subAreaName, Long zipcodes,Long masterAreaId, boolean serving, boolean servingParty) {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		MasterDeliveryArea area = null;
		if(null != masterAreaId) {
			area = (MasterDeliveryArea) session.get(MasterDeliveryArea.class, masterAreaId);
		}
		DeliveryArea deliveryArea = new DeliveryArea();
		deliveryArea.setAreaName(areaName);
		deliveryArea.setSubAreaName(subAreaName);
		List<DeliveryArea> areaList = new ArrayList<DeliveryArea>();
		areaList.add(deliveryArea);
		AvailableZipcodes areaZipcodes =  null;
		if(null != zipcodes) {
			 areaZipcodes =  (AvailableZipcodes) session.get(AvailableZipcodes.class, zipcodes);
			 if(areaZipcodes == null) {
				areaZipcodes = new AvailableZipcodes();
				areaZipcodes.setZipcode(zipcodes);
				session.save(areaZipcodes);
			 }
		} 
		
		deliveryArea.setZipcodes(areaZipcodes);
			
		deliveryArea.setServing(serving);
		deliveryArea.setMasterArea(area);
		deliveryArea.setServingParty(servingParty);
		session.save(deliveryArea);
		areaZipcodes.setArea(areaList);	
		session.getTransaction().commit();
		session.close();
	}

	@SuppressWarnings("unchecked")
	public MasterDeliveryArea getMasterDeliveryArea(String areaName) {
		Session session = this.sessionFactory.openSession();

		List<MasterDeliveryArea> allAvailableDeliveryArea = (List<MasterDeliveryArea>) session
				.createCriteria(MasterDeliveryArea.class).add(Restrictions.eq("areaName", areaName)).list();
		if(null!= allAvailableDeliveryArea && !allAvailableDeliveryArea.isEmpty()) {
			return allAvailableDeliveryArea.get(0);
			
		}
		return null;
	}
	public DeliveryArea getDeliveryArea(Long areaId) {
		Session session = this.sessionFactory.openSession();

		DeliveryArea deliveryArea = (DeliveryArea) session
				.get(DeliveryArea.class, areaId);
		
		return deliveryArea;
	}

	@SuppressWarnings("unchecked")
	public List<MasterDeliveryArea> getAllMasterAvailableDeliveryArea() {
		Session session = this.sessionFactory.openSession();

		List<MasterDeliveryArea> allAvailableDeliveryArea = (List<MasterDeliveryArea>) session
				.createCriteria(MasterDeliveryArea.class).list();
		return allAvailableDeliveryArea;
	}
	@SuppressWarnings("unchecked")
	public List<AreaSubAreaVo> getAllAvailableDeliveryArea() {
		Session session = this.sessionFactory.openSession();

		List<DeliveryArea> allAvailableDeliveryArea = (List<DeliveryArea>) session
				.createCriteria(DeliveryArea.class).list();
		List<AreaSubAreaVo> areaList = new ArrayList<AreaSubAreaVo>();
		List<AreaVo> subAreaList = null;
		Set<String> zipcodes = null;
		if(null!= allAvailableDeliveryArea) {
			AreaSubAreaVo areaSubAreaVo = null;
			AreaVo subAreaVo = null;
			for(DeliveryArea area :allAvailableDeliveryArea) {
				boolean areaExist =false;
				if(!areaList.isEmpty()) {
					
					for(AreaSubAreaVo areaVo:areaList) {
						if(areaVo.getAreaName().equals(area.getAreaName())) {
							subAreaVo = new AreaVo();
							subAreaVo.setAreaName(area.getSubAreaName());
							subAreaVo.setDeliveryAreaId(area.getDeliveryAreaId());
							subAreaVo.setZipcode(String.valueOf(area.getZipcodes().getZipcode()));
							subAreaVo.setServingIndividual(area.isServing());
							subAreaVo.setServingParty(area.isServingParty());
							areaVo.getSubArea().add(subAreaVo);
							areaVo.getZipcodes().add(String.valueOf(area.getZipcodes().getZipcode()));
							areaExist =true;
							break;
						} 
					}
					
				} 
				if(!areaExist) {
					areaSubAreaVo = new AreaSubAreaVo();
					areaSubAreaVo.setAreaName(area.getAreaName());
					subAreaList = new ArrayList<AreaVo>();
					subAreaVo = new AreaVo();
					subAreaVo.setAreaName(area.getSubAreaName());
					subAreaVo.setDeliveryAreaId(area.getDeliveryAreaId());
					subAreaVo.setZipcode(String.valueOf(area.getZipcodes().getZipcode()));
					subAreaVo.setServingIndividual(area.isServing());
					subAreaVo.setServingParty(area.isServingParty());					
					subAreaList.add(subAreaVo);
					areaSubAreaVo.setSubArea(subAreaList);
					zipcodes = new HashSet<String>();
					zipcodes.add(String.valueOf(area.getZipcodes().getZipcode()));
					areaSubAreaVo.setZipcodes(zipcodes);
					areaList.add(areaSubAreaVo);
				}
				
			}
		}
		return areaList;
	}

	@SuppressWarnings("unchecked")
	public List<AvailableZipcodes> isInAvaialbleDeliveryArea(long zipcode) {
		Session session = this.sessionFactory.openSession();
		List<AvailableZipcodes> allAvailableCrews = (List<AvailableZipcodes>) session
				.createCriteria(AvailableZipcodes.class).add(Restrictions.eq("zipcode", zipcode)).list();
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
