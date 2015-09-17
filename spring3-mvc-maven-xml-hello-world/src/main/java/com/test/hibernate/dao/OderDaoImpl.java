package com.test.hibernate.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.panipuri.vo.ItemVo;
import com.panipuri.vo.ToppingVo;
import com.test.hibernate.AvailableTopping;
import com.test.hibernate.Item;
import com.test.hibernate.Order;
import com.test.hibernate.OrderItems;
import com.test.hibernate.OrderToppings;
import com.test.hibernate.Status;

public class OderDaoImpl  {
	private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }
	public void addOrder(List<ItemVo> itemList, List<ToppingVo> toppingList) {
		Order order = new Order();
		List<OrderItems> orderItems = new ArrayList<OrderItems>();
		List<OrderToppings> orderToppings = new ArrayList<OrderToppings>();
		if(null != itemList) {
			for(ItemVo item : itemList) {
				OrderItems orderItem = new OrderItems();
				Item itemInfo = new Item();
				itemInfo.setItemId(item.getItemId());
				orderItem.setItem(itemInfo);
				orderItem.setQuantity(item.getItemQuantity());
				orderItems.add(orderItem);
			}
		}
		if(null != toppingList) {
			for(ToppingVo topping : toppingList) {
				OrderToppings ordertopping = new OrderToppings();
				AvailableTopping availableTopping = new AvailableTopping();
				availableTopping.setToppingId(topping.getToppingId());
				ordertopping.setTopping(availableTopping);
				ordertopping.setQuantity(topping.getQuantity());
				orderToppings.add(ordertopping);
			}
		}
		order.setOrderItems(orderItems);
		order.setOrderToppings(orderToppings);
		order.setStatus(Status.INITIATED);
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		session.save(order);
		session.getTransaction().commit();
		session.close();
	}
	public Order getOrderDetails(String orderId) {
		Session session = this.sessionFactory.getCurrentSession();
		Order orderDetails = (Order)session.get(Order.class, orderId);
		return orderDetails;
	}
	
}
