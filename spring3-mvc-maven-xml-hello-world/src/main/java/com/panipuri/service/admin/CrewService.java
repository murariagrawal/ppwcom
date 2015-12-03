package com.panipuri.service.admin;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.panipuri.vo.AreaVo;
import com.test.hibernate.dao.CrewDaoImpl;

@Component
public class CrewService {
	@Autowired
	private CrewDaoImpl crewDaoImpl;
	public void addCrew(String crewName, String contactNumber, String address, long salary, AreaVo area) {
		crewDaoImpl.addCrew(crewName, contactNumber, address, salary, area);
	}
	public void updateCrew(String crewId,String crewName, String contactNumber, String address, long salary,AreaVo area) {
		crewDaoImpl.updateCrewDetails(crewName, contactNumber, salary, address, crewId);
	}
	public void getCrewDetails(String crewId) {
		crewDaoImpl.getCrewDetails(crewId);
	}
	public void getAllCrewInformation() {
		crewDaoImpl.getAllCrews();
	}
	public void getAllAvailableCrewInformation() {
		crewDaoImpl.getAllAvailableCrews();
	}
	public void updateCrewDetailsLocation(String crewId, BigDecimal latitude, BigDecimal longitude) {
		crewDaoImpl.updateCrewDetailsLocation(crewId, latitude, longitude);
	}

	/**
	 * @param crewDaoImpl the crewDaoImpl to set
	 */
	public void setCrewDaoImpl(CrewDaoImpl crewDaoImpl) {
		this.crewDaoImpl = crewDaoImpl;
	}
}
