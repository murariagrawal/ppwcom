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

import com.panipuri.service.AdminService;
import com.test.hibernate.DeliverySlot;
import com.test.hibernate.MasterDeliveryArea;
@Controller
public class AreaController {
	@Autowired
	private AdminService adminService;
	@RequestMapping(method = RequestMethod.POST, value="/addArea")
	public ModelAndView addArea(@RequestParam(value = "areaName") String areaName, 
			@RequestParam(value = "areaCity") String areaCity,
			@RequestParam(value = "areaState") String areaState,
			@RequestParam(value = "areaZipcode") String areazipcodes, HttpServletRequest request) {
		ModelAndView mv = null;
		List<Long> zipcodes= new ArrayList<Long>();
		if(null!=areazipcodes) {
			String[] tokens = areazipcodes.split(",");
			for(int i=0;i<tokens.length;i++) {
				zipcodes.add(Long.parseLong(tokens[i]));
			}
		}
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
			deliverySlots.add(deliverySlot);
		}
		adminService.addArea(areaName, areaCity, areaState, zipcodes, deliverySlots);
		mv = new ModelAndView("adminHome");
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/fetchAllArea")
	public ModelAndView fetchAllArea() {
		ModelAndView mv = null;
		
		List<MasterDeliveryArea> allDeliveryAreas = adminService.fetchAllArea();
		mv = new ModelAndView("adminHome");
		mv.addObject("masterDeliveryArea", allDeliveryAreas);
		return mv;
	}
}
