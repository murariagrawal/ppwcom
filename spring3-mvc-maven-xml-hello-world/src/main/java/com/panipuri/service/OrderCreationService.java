package com.panipuri.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.panipuri.SMSSender;
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
			String contactNo = otpDaoImpl.addOTP(orderId,otp);
			String message = "Dear Customer, You have initiated an order with us. Your One time Password for verification is "+otp;
			SMSSender smsSender = new SMSSender();
			smsSender.sendCustomerSMS(contactNo, message);
			statusVo.setStatus(true);
		} catch (Exception e) {
			statusVo.setStatus(false);
		}
		
		return statusVo;
	}
	
	public OrderVo validateOTP(String otp, String orderId) throws Exception {
		
		OrderVo orderDetails = null;
		
		OneTimePassword oneTimePassword = otpDaoImpl.getOTPDetails(orderId,otp);
		if(null != oneTimePassword) {
			orderDetails = orderDaoImpl.confirmOrder(orderId);
			StringBuffer orderDetailsString= new StringBuffer("");
			if(null != orderDetails.getItemList()) {
				Iterator<ItemVo> itemIterator = orderDetails.getItemList().iterator();
				ItemVo item= null;
				while(itemIterator.hasNext()) {
					item = itemIterator.next();
					orderDetailsString.append(item.getItemQuantity());
					orderDetailsString.append(" pack of ");
					orderDetailsString.append(item.getItemName());
					if(itemIterator.hasNext()) {
						orderDetailsString.append(",");
					} else {
						if(null != orderDetails.getToppingList() || !orderDetails.getToppingList().isEmpty()) {
							Iterator<ToppingVo> toppingsIterator = orderDetails.getToppingList().iterator();
							if(itemIterator.hasNext()) {
								orderDetailsString.append(",");
							}
						} else {
							orderDetailsString.append(".");
						}
					}
				}
			}
			if(null != orderDetails.getToppingList()) {
				Iterator<ToppingVo> toppingsIterator = orderDetails.getToppingList().iterator();
				ToppingVo topping= null;
				while(toppingsIterator.hasNext()) {
					topping = toppingsIterator.next();
					orderDetailsString.append(topping.getQuantity()).append(" additional ");					
					orderDetailsString.append(topping.getToppingName());
					orderDetailsString.append(" Stuffing ");
					if(toppingsIterator.hasNext()) {
						orderDetailsString.append(",");
					} else {
						orderDetailsString.append(".");
					}
				}
			}
			
			String message = "Dear Customer, Thank you for placing and order with PaniPuri Bites. Your order Details: "+orderDetailsString.toString();
			SMSSender smsSender = new SMSSender();
			smsSender.sendCustomerSMS(orderDetails.getContactNo(), message);
			
		} else {
			Exception e = new Exception("ERR_INVALID_OTP");
			throw e;
		}
		
		return orderDetails;
	}
	
}
