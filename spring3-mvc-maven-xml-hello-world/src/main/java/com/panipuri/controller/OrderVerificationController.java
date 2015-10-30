package com.panipuri.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.panipuri.service.OrderFetchService;
import com.test.hibernate.Order;

@Controller
public class OrderVerificationController {
	@Autowired
	private OrderFetchService orderFetchService;
	@RequestMapping(method = RequestMethod.POST, value="/verifyDetails")
	public ModelAndView showVerifyOrder(@RequestParam(value = "deliveryOrderId") String orderId) {
		System.out.println("in controller");
		ModelAndView mv = null;
		Order orderDetails = orderFetchService.getOrderDetails(orderId);
			
			mv = new ModelAndView("verifyOrder");
			mv.addObject("orderDetails", orderDetails);
		return mv;
	}
}
