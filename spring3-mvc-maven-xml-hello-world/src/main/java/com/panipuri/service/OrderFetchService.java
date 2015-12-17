package com.panipuri.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.panipuri.vo.ItemVo;
import com.panipuri.vo.OrderVo;
import com.panipuri.vo.ToppingVo;
import com.test.hibernate.Address;
import com.test.hibernate.Customer;
import com.test.hibernate.dao.OderDaoImpl;
@Component
@Lazy
public class OrderFetchService {
	@Autowired
	private OderDaoImpl oderDaoImpl;
	public OrderVo updateOrderAndgetOrderDetails(String orderId, Address address,  Customer customer, String slotId) {
		
		OrderVo orderDetails = oderDaoImpl.getOrderDetails(orderId, address, customer, slotId);
		
		calculateTotalOrderPrice(orderDetails);
		return orderDetails;
		
	}
	public void calculateTotalOrderPrice(OrderVo orderDetails) {
		List<ItemVo> itemList = orderDetails.getItemList();
		List<ToppingVo> toppingList = orderDetails.getToppingList();
		Float totalPrice = new Float(0);
		if(null!=itemList) {
			for(ItemVo item: itemList) {
				totalPrice = totalPrice+item.getItemPrice().floatValue();
			}
		}
		if(null!=toppingList) {
			for(ToppingVo topping: toppingList) {
				totalPrice = totalPrice+topping.getPrice().floatValue();
			}
		}
		orderDetails.setTotalPrice(totalPrice);
	}
	public void setOderDaoImpl(OderDaoImpl oderDaoImpl) {
		this.oderDaoImpl = oderDaoImpl;
	}

	

	
}
