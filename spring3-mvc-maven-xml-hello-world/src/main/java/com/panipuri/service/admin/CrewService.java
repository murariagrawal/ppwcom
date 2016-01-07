package com.panipuri.service.admin;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.panipuri.vo.AreaVo;
import com.test.hibernate.Crew;
import com.test.hibernate.dao.CrewDaoImpl;

@Component
public class CrewService {
	@Autowired
	private CrewDaoImpl crewDaoImpl;
	public void addCrew(Crew crewInfo, AreaVo area) {
		crewDaoImpl.addCrew(crewInfo, area);
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
	public Crew loginCrew(String userId, String password) {
		int i =0;
		Crew crewInfo = crewDaoImpl.validateCrewCredential(userId, password);
		return crewInfo;
	}

	/**
	 * @param crewDaoImpl the crewDaoImpl to set
	 */
	public void setCrewDaoImpl(CrewDaoImpl crewDaoImpl) {
		this.crewDaoImpl = crewDaoImpl;
	}
}
