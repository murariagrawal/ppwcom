package com.panipuri.controller.admin;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.panipuri.service.admin.CrewService;
import com.panipuri.vo.AreaVo;
import com.test.hibernate.Crew;

@Controller

public class CrewController {
	@Autowired
	private CrewService crewService;
	@RequestMapping(method = RequestMethod.POST, value="/addCrew")
	public ModelAndView addCrew(final HttpServletRequest request) {
		String crewName = request.getParameter("crewName");
		String contactNumber = request.getParameter("crewPhone");
		String address = request.getParameter("crewAddress");
		String salary = request.getParameter("salary");
		String userId = request.getParameter("crewUserId");
		String password = request.getParameter("crewPassword");
		String partyCrew = request.getParameter("partyCrew");
		
		String areaId = request.getParameter("masterAreaCrew");
		AreaVo area = null;	
		if (areaId != null) {            
			 area = new AreaVo();
    		 area.setDeliveryAreaId(new Long(areaId));
		}
		Long salaryLong= new Long(0);
		if(salary != null && salary.equals("")) {
			salaryLong =  new Long(salary);
		}
		Crew crewInfo = new Crew();
		crewInfo.setAddress(address);
		crewInfo.setContactnumber(contactNumber);
		crewInfo.setName(crewName);
		crewInfo.setPassword(password);
		if(null != partyCrew && partyCrew.equals("true")) {
			crewInfo.setParty(true);
		}
		if(salary != null && !salary.equals("")) {
			crewInfo.setSalary(new Long(salary));
		}
		crewInfo.setUserId(userId);
		crewService.addCrew(crewInfo, area);
		return null;
	}
	@RequestMapping(method = RequestMethod.POST, value="/updateCrew")
	public ModelAndView updateCrew(final HttpServletRequest request,
			@RequestParam(value = "crewId") String crewId) {
		String address = request.getParameter("crewAddress");
		String salary = request.getParameter("salary");
		String crewName = request.getParameter("crewName");
		String contactNumber = request.getParameter("contactNumber");
		String areaId = request.getParameter("areaId");
		Long salaryLong= new Long(0);
		AreaVo area = new AreaVo();
		if(areaId != null && areaId.equals("")) {
			area.setDeliveryAreaId(new Long(areaId));
		}
		if(salary != null && salary.equals("")) {
			salaryLong =  new Long(salary);
		}
		crewService.updateCrew(crewId, crewName, contactNumber, address, salaryLong, area);
		return null;
	}
	@RequestMapping(method = RequestMethod.POST, value="/updateCrewLocation")
	public ModelAndView updateCrewLocation(final HttpServletRequest request,
			@RequestParam(value = "crewId") String crewId) {
		String latitudeString = request.getParameter("latitude");
		String longitudeString = request.getParameter("longitude");
		BigDecimal latitude = null;
		BigDecimal longitude = null;
		if(null!=latitudeString) {
			latitude = new BigDecimal(latitudeString);
		} 
		if (null != longitudeString) {
			longitude = new BigDecimal(longitudeString);
		}
		crewService.updateCrewDetailsLocation(crewId,latitude, longitude);
		return null;
	}
	@RequestMapping(method = RequestMethod.GET, value="/getAllCrewDetails")
	public ModelAndView getAllCrewDetails() {
		crewService.getAllCrewInformation();
		return null;
	}
	@RequestMapping(method = RequestMethod.POST, value="/loginCrew")
	public ModelAndView loginCrew(@RequestParam("userId") String userId,@RequestParam("password") String password ) {
		Crew crew = crewService.loginCrew(userId, password);
		int i = 0;
		ModelAndView mv = null;
		mv = new ModelAndView("");
		if(null != crew) {
			
			mv.addObject("loginSucceded", true);
		} else {
			
			mv.addObject("loginSucceded", false);
		}
	
		return mv;
		
	}
	
	/**
	 * @param crewService the crewService to set
	 */
	public void setCrewService(CrewService crewService) {
		this.crewService = crewService;
	}
}
