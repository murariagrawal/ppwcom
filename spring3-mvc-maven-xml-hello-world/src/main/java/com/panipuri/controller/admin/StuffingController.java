package com.panipuri.controller.admin;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.panipuri.service.AdminService;
import com.panipuri.vo.ToppingVo;
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
		mv = new ModelAndView("adminHome");
		return mv;
	}
}
