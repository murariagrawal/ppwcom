package com.panipuri.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OrderPaymentController {
	@RequestMapping("/onlinePaymentOptions")
	public ModelAndView showOnlinePaymentOptions() {
		System.out.println("in controller");
		ModelAndView mv = null;
		
			
			mv = new ModelAndView("onlinePaymentOptions");
		return mv;
	}
}
