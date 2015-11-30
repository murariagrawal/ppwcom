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
import com.test.hibernate.DeliveryArea;
import com.test.hibernate.DeliverySlot;
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
		Long zipcodeLong = null;
		if(null != zipcode && !zipcode.equals("")) {
			zipcodeLong = new Long(zipcode);
		}
		boolean serving = false;
		if(null != servingStr && servingStr.equals("true")) {
			serving = true;
		}
		adminService.addArea(areaName, subAreaName, zipcodeLong, masterAreaId, serving);
		mv = new ModelAndView("adminHome");
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/fetchAllArea")
	public ModelAndView fetchAllArea() {
		ModelAndView mv = null;
		
		List<DeliveryArea> allDeliveryAreas = adminService.fetchAllArea();
		mv = new ModelAndView("adminHome");
		mv.addObject("masterDeliveryArea", allDeliveryAreas);
		return mv;
	}
}
