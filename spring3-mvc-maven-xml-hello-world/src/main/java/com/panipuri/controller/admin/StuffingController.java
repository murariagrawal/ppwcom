package com.panipuri.controller.admin;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.panipuri.service.admin.AdminService;
import com.panipuri.vo.ToppingVo;
import com.test.hibernate.AvailableTopping;
@Controller
public class StuffingController {
	@Autowired
	private AdminService adminService;
	@RequestMapping(method = RequestMethod.POST, value="/addStuffing")
	public ModelAndView addStuffing(@RequestParam(value = "stuffingName") String itemName, 
			@RequestParam(value = "stuffingPrice") String itemPrice) {
		ModelAndView mv = null;
		ToppingVo topping = new ToppingVo();
		topping.setToppingName(itemName);
		topping.setPrice(new BigDecimal(itemPrice));
		adminService.addStuffing(topping);
		mv = new ModelAndView("");
		return mv;
	}
	@RequestMapping(method = RequestMethod.GET, value="/getAllStuffing")
	public ModelAndView getAllStuffing() {
		ModelAndView mv = null;
		
		List<AvailableTopping> stuffingList = adminService.getAllStuffing();
		mv = new ModelAndView("");
		mv.addObject("stuffingList",stuffingList);
		return mv;
	}
	@RequestMapping(method = RequestMethod.POST, value="/updateStuffing")
	public ModelAndView updateStuffing(@RequestParam(value = "selectUpdateStuffing") String stuffingId, 
			@RequestParam(value = "stuffingUpdatePrice") String stuffingPrice) {
		ModelAndView mv = null;
		ToppingVo topping = new ToppingVo();
		topping.setToppingId(new Long(stuffingId));
		topping.setPrice(new BigDecimal(stuffingPrice));
		adminService.updateStuffing(topping);
		mv = new ModelAndView("");
		return mv;
	}
	@RequestMapping(method = RequestMethod.POST, value="/deleteStuffing")
	public ModelAndView deleteStuffing(@RequestParam(value = "selectDeleteStuffing") String stuffingId, 
			@RequestParam(value = "stuffingDeletePrice") String stuffingPrice) {
		ModelAndView mv = null;
		ToppingVo topping = new ToppingVo();
		topping.setToppingId(new Long(stuffingId));
		topping.setPrice(new BigDecimal(stuffingPrice));
		adminService.deleteStuffing(topping);
		mv = new ModelAndView("");
		return mv;
	}
}
