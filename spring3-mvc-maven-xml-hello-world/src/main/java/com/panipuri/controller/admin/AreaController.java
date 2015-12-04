package com.panipuri.controller.admin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.panipuri.service.admin.AdminService;
import com.panipuri.vo.AreaSubAreaVo;
import com.test.hibernate.DeliverySlot;
import com.test.hibernate.MasterDeliveryArea;
@Controller
public class AreaController {
	@Autowired
	private AdminService adminService;
	@RequestMapping(method = RequestMethod.POST, value="/addMasterArea")
	public ModelAndView addMasterArea(@RequestParam(value = "areaName") String areaName, 
			@RequestParam(value = "areaCity") String areaCity,
			@RequestParam(value = "areaState") String areaState, HttpServletRequest request) {
		ModelAndView mv = null;
		
		String[] slots;
		slots = request.getParameterValues("deliverySlots");
		String[] quantity;
		quantity = request.getParameterValues("dliveryQuantity");
		
		List<DeliverySlot> deliverySlots = new ArrayList<DeliverySlot>();
		DeliverySlot deliverySlot = null;
		for(int j = 0; j < slots.length; j++) {
			deliverySlot = new DeliverySlot();
			String[] slotTimes = slots[j].split("-");
			deliverySlot.setStartTime(slotTimes[0]+"PM");
			deliverySlot.setEndTime(slotTimes[1]+"PM");
			deliverySlot.setSlotQuantity(Integer.parseInt(quantity[j]));
			deliverySlot.setTodaySlotQuantity(Integer.parseInt(quantity[j]));
			deliverySlots.add(deliverySlot);
		}
		adminService.addMasterArea(areaName, areaCity, areaState, deliverySlots);
		mv = new ModelAndView("adminHome");
		return mv;
	}
	@RequestMapping(method = RequestMethod.POST, value="/addArea")
	public ModelAndView addArea(@RequestParam(value = "deliveryAreaName") String areaName, 
			 HttpServletRequest request) {
		ModelAndView mv = null;
		
		String subAreaName = request.getParameter("deliverySubAreaName");
		String zipcode = request.getParameter("areaZipcode");
		String masterAreaId = request.getParameter("masterArea");
		String servingStr = request.getParameter("serving");
		String servingPartyStr = request.getParameter("servingParty");
		Long zipcodeLong = null;
		Long masterAreaIdLong = null;
		if(null != zipcode && !zipcode.equals("")) {
			zipcodeLong = new Long(zipcode);
		}
		if(null != masterAreaId && !masterAreaId.equals("")) {
			masterAreaIdLong = new Long(masterAreaId);
		}
		boolean serving = false;
		if(null != servingStr && servingStr.equals("on")) {
			serving = true;
		}
		boolean servingParty = false;
		if(null != servingPartyStr && servingPartyStr.equals("on")) {
			servingParty = true;
		}
		adminService.addArea(areaName, subAreaName, zipcodeLong, masterAreaIdLong, serving, servingParty);
		mv = new ModelAndView("adminHome");
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/fetchAllArea")
	public ModelAndView fetchAllArea() {
		ModelAndView mv = null;
		
		List<AreaSubAreaVo> allDeliveryAreas = adminService.fetchAllArea();
		mv = new ModelAndView("adminHome");
		mv.addObject("deliveryArea", allDeliveryAreas);
		return mv;
	}
	@RequestMapping(method = RequestMethod.GET, value="/fetchAllMasterArea")
	public ModelAndView fetchAllMasterArea() {
		ModelAndView mv = null;
		
		List<MasterDeliveryArea> allDeliveryAreas = adminService.fetchAllMasterArea();
		mv = new ModelAndView("adminHome");
		mv.addObject("masterDeliveryArea", allDeliveryAreas);
		return mv;
	}
}
