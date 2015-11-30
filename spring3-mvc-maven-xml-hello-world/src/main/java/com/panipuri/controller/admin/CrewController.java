package com.panipuri.controller.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.panipuri.service.admin.CrewService;
import com.panipuri.vo.AreaVo;

@Controller
public class CrewController {
	@Autowired
	private CrewService crewService;
	@RequestMapping(method = RequestMethod.POST, value="/addCrew")
	public ModelAndView addCrew(final HttpServletRequest request,
			@RequestParam(value = "crewName") String crewName, 
			@RequestParam(value = "contactNumber") String contactNumber) {
		String address = request.getParameter("crewAddress");
		String salary = request.getParameter("salary");
		String[] areaIds;
		areaIds = request.getParameterValues("areaId");
		List<AreaVo> areaVoList = new ArrayList<AreaVo>();	
		if (areaIds != null) {
            
            AreaVo area = null;
            for(int j = 0; j < areaIds.length; j++) {
				 area = new AreaVo();
        		 area.setDeliveryAreaId(new Long(areaIds[j]));	
        		 areaVoList.add(area);
			}
		}
		Long salaryLong= new Long(0);
		if(salary != null && salary.equals("")) {
			salaryLong =  new Long(salary);
		}
		crewService.addCrew(crewName, contactNumber, address, salaryLong, areaVoList);
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
	@RequestMapping(method = RequestMethod.POST, value="/getCrewDetails")
	public ModelAndView getCrewDetails() {
		return null;
	}
	/**
	 * @param crewService the crewService to set
	 */
	public void setCrewService(CrewService crewService) {
		this.crewService = crewService;
	}
}
