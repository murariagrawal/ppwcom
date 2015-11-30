package com.panipuri.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.panipuri.service.OrderCreationService;
import com.panipuri.vo.AddressVo;
import com.panipuri.vo.AreaVo;
import com.panipuri.vo.OrderVo;
import com.panipuri.vo.StatusVo;

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
		
		StatusVo status = orderCreationService.sendOTP(orderId);
		status.setMessage("SMS has been sent. Please verify the 6 digit code.");
		mv = new ModelAndView("");
		mv.addObject("message",status.getMessage());
		mv.addObject("success",status.isStatus());
		
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/validateOTP")
	public ModelAndView validateOTP(final HttpServletRequest request) {
		ModelAndView mv = null;
		String orderId = request.getParameter("orderIdPayment");
		String otp = request.getParameter("otpCode");
		AddressVo addressVo = null;
		OrderVo orderDetails = null;
		String addressLine = "";
		String landmarkReturn = ""; 
		String errorMessageString ="";
		String zipcodeReturn = "";
		try {
			orderDetails = orderCreationService.validateOTP(otp, orderId);
			addressVo = orderDetails.getDeliveryAddress();			
			if(null != addressVo.getAddressLine1()) {
				addressLine= addressLine+addressVo.getAddressLine1();
				if(null!=addressVo.getAddressline2()){
					addressLine =addressLine+" ";
				}
			}
			if(null!=addressVo.getAddressline2()){
				addressLine =addressLine+addressVo.getAddressline2();
			}			
			if(null!=addressVo.getLandmark()){
				landmarkReturn =landmarkReturn+addressVo.getLandmark();
			}
			AreaVo areaDetails = addressVo.getArea();
			zipcodeReturn = areaDetails.getAreaName() + " " + areaDetails.getZipcode();
			
		} catch (Exception e) {
			String message = e.getMessage();
			if(null != message && !message.equals("")) {
				if(message.equals("ERR_INVALID_OTP")) {
					errorMessageString = "Invalid OTP enter. Please enter the valid otp sent via SMS.";
				} else {
					errorMessageString = "Something went wrong. Please try again.";
				}
			}
		}

		mv = new ModelAndView("");
		if(errorMessageString== null || errorMessageString.equals("")) {
			mv.addObject("orderId", orderDetails.getOrderId());
			mv.addObject("contactNo", orderDetails.getContactNo());
			mv.addObject("deliverySlot", orderDetails.getDeliverySlot());
			mv.addObject("addressLine", addressLine);
			mv.addObject("landmarkReturn", landmarkReturn);
			mv.addObject("zipcodeReturn", zipcodeReturn);
			mv.addObject("itemList", orderDetails.getItemList());
			mv.addObject("toppingList", orderDetails.getToppingList());																																	
		} else {
			mv.addObject("errormessage", errorMessageString);
		}
		return mv;
	}
}
