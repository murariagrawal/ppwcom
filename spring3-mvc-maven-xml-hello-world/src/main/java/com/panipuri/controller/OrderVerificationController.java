package com.panipuri.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.panipuri.service.OrderFetchService;
import com.panipuri.vo.OrderVo;

@Controller
public class OrderVerificationController {
	@Autowired
	private OrderFetchService orderFetchService;
	@RequestMapping("/verifyOrder")
	public ModelAndView showVerifyOrder(@RequestParam(value = "orderId") String orderId) {
		System.out.println("in controller");
		ModelAndView mv = null;
		OrderVo orderDetails = orderFetchService.getOrderDetails(orderId);
			
			mv = new ModelAndView("verifyOrder");
			mv.addObject("orderDetails", orderDetails);
		return mv;
	}
}
