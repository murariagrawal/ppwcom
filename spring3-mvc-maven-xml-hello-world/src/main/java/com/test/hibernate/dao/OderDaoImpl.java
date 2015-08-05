package com.test.hibernate.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.panipuri.valueobjects.ItemVo;
import com.panipuri.valueobjects.ToppingsVo;
import com.test.hibernate.Order;

public class OderDaoImpl extends HibernateDaoSupport {
	public void addOrder(List<ItemVo> itemList, List<ToppingsVo> toppingList) {
		
	}
	public Order getOrderDetails(String orderId) {
		Order orderDetails = (Order)getHibernateTemplate().get(Order.class, orderId);
		return orderDetails;
	}
	
}
