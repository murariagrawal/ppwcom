package com.panipuri.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.panipuri.service.OrderCreationService;

@Controller
public class OrderPaymentController {
	@Autowired
	private OrderCreationService orderCreationService;
	@RequestMapping("/onlinePaymentOptions")
	public ModelAndView showOnlinePaymentOptions() {
		System.out.println("in controller");
		ModelAndView mv = null;
		
			
			mv = new ModelAndView("onlinePaymentOptions");
		return mv;
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value="/sendOTP")
	public ModelAndView sendOTP(final HttpServletRequest request) {
		ModelAndView mv = null;
		String orderId = request.getParameter("orderIdPayment");
		mv=new ModelAndView("");
		return mv;
	}
}
