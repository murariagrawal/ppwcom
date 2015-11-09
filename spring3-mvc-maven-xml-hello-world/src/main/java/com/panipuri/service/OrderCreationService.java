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
import com.test.hibernate.dao.OderDaoImpl;
@Component
@Lazy
public class OrderCreationService {
	@Autowired
	OderDaoImpl oderDaoImpl;
	public Long createOrder(List<ItemVo> selectedItems, List<ToppingVo> selectedToppings, String orderId) {
		Long orderIdLong = oderDaoImpl.addOrder(selectedItems, selectedToppings, orderId);
		
		return orderIdLong;
	}
	public StatusVo sendOTP(String orderId) {
		OrderVo orderDetails = oderDaoImpl.getOrder(orderId);
		orderDetails.getContactNo();
		String otp = SimpleOTPGenerator.random(6);
		String message = "Dear Customer, You have initiated an order with us. Your One time Password for verification is "+otp;
		return null;
	}
}
