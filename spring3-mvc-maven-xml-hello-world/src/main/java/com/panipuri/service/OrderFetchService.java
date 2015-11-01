package com.panipuri.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.panipuri.vo.AddressVo;
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
	public OrderVo updateOrderAndgetOrderDetails(String orderId, Address address,  Customer customer) {
		
		OrderVo orderDetails = oderDaoImpl.getOrderDetails(orderId, address, customer);
		orderDetails.setToppingList(getToppingList());
		calculateTotalOrderPrice(orderDetails);
		return orderDetails;
		
	}
	private void calculateTotalOrderPrice(OrderVo orderDetails) {
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

	private List<ToppingVo> getToppingList() {
		List<ToppingVo> toppingList = new ArrayList<ToppingVo>();
		ToppingVo topping1 =  new ToppingVo();
		topping1.setToppingId(1);
		topping1.setToppingName("Mung");
		topping1.setQuantity(2);
		topping1.setPrice(new BigDecimal(5));
		ToppingVo topping2 =  new ToppingVo();
		topping2.setToppingId(2);
		topping2.setToppingName("onion");
		topping2.setQuantity(2);
		topping2.setPrice(new BigDecimal(5));
		toppingList.add(topping1);
		toppingList.add(topping2);
		return toppingList;
	}

	
}
