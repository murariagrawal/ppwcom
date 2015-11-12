package com.panipuri.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.panipuri.SimpleOTPGenerator;
import com.panipuri.vo.ItemVo;
import com.panipuri.vo.OrderVo;
import com.panipuri.vo.StatusVo;
import com.panipuri.vo.ToppingVo;
import com.test.hibernate.OneTimePassword;
import com.test.hibernate.dao.OTPDaoImpl;
import com.test.hibernate.dao.OderDaoImpl;
@Component
@Lazy
public class OrderCreationService {
	@Autowired
	OderDaoImpl orderDaoImpl;
	@Autowired
	OTPDaoImpl otpDaoImpl;
	public Long createOrder(List<ItemVo> selectedItems, List<ToppingVo> selectedToppings, String orderId) {
		Long orderIdLong = orderDaoImpl.addOrder(selectedItems, selectedToppings, orderId);
		
		return orderIdLong;
	}
	public StatusVo sendOTP(String orderId) {
		StatusVo statusVo =new StatusVo();
		try {
		
			String otp = SimpleOTPGenerator.random(6);
			otpDaoImpl.addOTP(orderId,otp);
			String message = "Dear Customer, You have initiated an order with us. Your One time Password for verification is "+otp;
			
			statusVo.setStatus(true);
		} catch (Exception e) {
			statusVo.setStatus(false);
		}
		
		return statusVo;
	}
	
	public OrderVo validateOTP(String otp, String orderId) {
		
		OrderVo orderDetails = null;
		
		OneTimePassword oneTimePassword = otpDaoImpl.getOTPDetails(orderId,otp);
		if(null != oneTimePassword) {
			orderDetails = orderDaoImpl.confirmOrder(orderId);
		} else {
			
		}
		
		return orderDetails;
	}
	
}
