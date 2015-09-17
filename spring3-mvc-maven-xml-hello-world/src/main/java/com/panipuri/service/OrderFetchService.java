package com.panipuri.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.panipuri.vo.AddressVo;
import com.panipuri.vo.ItemVo;
import com.panipuri.vo.OrderVo;
import com.panipuri.vo.ToppingVo;
@Component
@Lazy
public class OrderFetchService {
	public OrderVo getOrderDetails(String orderId) {
		OrderVo orderVo = new OrderVo();
		List<ItemVo> itemList = getItemList();
		List<ToppingVo> toppingList = getToppingList();
		BigDecimal totalPrice = new BigDecimal(0);
		for(ItemVo item :itemList) {
			BigDecimal tempPrice = item.getItemPrice();			
			tempPrice.multiply(new BigDecimal(item.getItemQuantity()));
			totalPrice.add(tempPrice);
		}
		for(ToppingVo topping :toppingList) {
			BigDecimal tempPrice = topping.getPrice();			
			tempPrice.multiply(new BigDecimal(topping.getQuantity()));
			totalPrice.add(tempPrice);
		}
		
		AddressVo deliveryAddress = getDeliveryAddress();
		orderVo.setOrderId(121);
		orderVo.setDeliveryAddress(deliveryAddress);
		orderVo.setDeliverySlot("4Pm-5Pm");
		orderVo.setItemList(itemList);
		orderVo.setToppingList(toppingList);
		orderVo.setTotalPrice(totalPrice);
		return orderVo;
		
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

	private AddressVo getDeliveryAddress() {
		AddressVo deliveryAddress = new AddressVo();
		deliveryAddress.setAddressId(1121);
		deliveryAddress.setAddressLine1("D 303, Kool homes");
		deliveryAddress.setAddressline2("Nda Road, bavdhan");
		deliveryAddress.setCity("Pune");
		deliveryAddress.setContactNumber("9673142211");
		deliveryAddress.setState("Maharashtra");
		deliveryAddress.setZipcode("411021");
		return deliveryAddress;
	}

	private List<ItemVo> getItemList() {
		List<ItemVo> itemList = new ArrayList<ItemVo>();
		ItemVo item1 = new ItemVo();
		item1.setItemId(1212);
		item1.setItemName("Pack of 8");
		item1.setItemPrice(new BigDecimal(45));
		item1.setItemQuantity(2);
		ItemVo item2 = new ItemVo();
		item2.setItemId(1213);
		item2.setItemName("Pack of 8");
		item2.setItemPrice(new BigDecimal(45));
		item2.setItemQuantity(2);
		itemList.add(item1);
		itemList.add(item2);
		return itemList;
	}
}
