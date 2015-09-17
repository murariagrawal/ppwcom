package com.panipuri.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OrderConfirmationController {
	@RequestMapping("/completeOnlineOrder")
	public ModelAndView completeOnlineOrder() {
		System.out.println("in controller");
		ModelAndView mv = null;
		
			
			mv = new ModelAndView("orderConfirmation");
		return mv;
	}
	@RequestMapping("/completeCODOrder")
	public ModelAndView completeCODOrder() {
		System.out.println("in controller");
		ModelAndView mv = null;
		
			
			mv = new ModelAndView("orderConfirmation");
		return mv;
	}
}
