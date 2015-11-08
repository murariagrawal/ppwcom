package com.test.hibernate.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.panipuri.vo.AddressVo;
import com.panipuri.vo.ItemVo;
import com.panipuri.vo.OrderVo;
import com.panipuri.vo.ToppingVo;
import com.test.hibernate.Address;
import com.test.hibernate.AvailableTopping;
import com.test.hibernate.Customer;
import com.test.hibernate.DeliverySlot;
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
	public Long addOrder(List<ItemVo> itemList, List<ToppingVo> toppingList, String orderId) {
		Order order = null;
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		if(null !=orderId && !orderId.equals("")) {
			 order = (Order)session.get(Order.class, new Long(orderId));
		} else {
			order = new Order();
		}
		List<OrderItems> orderItems = new ArrayList<OrderItems>();
		List<OrderToppings> orderToppings = new ArrayList<OrderToppings>();
		if(null != itemList) {
			for(ItemVo item : itemList) {
				OrderItems orderItem = new OrderItems();
				Item itemInfo = new Item();
				itemInfo.setItemId(item.getItemId());
				orderItem.setItem(itemInfo);
				orderItem.setQuantity(item.getItemQuantity());
				orderItem.setOrder(order);
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
				ordertopping.setOrder(order);
				orderToppings.add(ordertopping);
			}
		}
		order.setOrderItems(orderItems);
		order.setOrderToppings(orderToppings);
		order.setStatus(Status.INITIATED);
		
		session.save(order);
		session.getTransaction().commit();
		session.close();
		return order.getOrderId();
	}
	public OrderVo getOrderDetails(String orderId, Address address, Customer customer, String slotId) {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		DeliverySlot slot = (DeliverySlot)session.get(DeliverySlot.class, new Long(slotId));
		Order orderDetails = (Order)session.get(Order.class, new Long(orderId));
		List<Customer> sessionCustomerList=(List<Customer>)session.createCriteria(Customer.class).add(Restrictions.eq("contactNo1", customer.getContactNo1())).list();
		if(null == sessionCustomerList || sessionCustomerList.isEmpty()) {
			address.setCustomer(customer);
			customer.getAddresses().add(address);
			session.saveOrUpdate(customer);
			
		} else {
			customer = sessionCustomerList.get(0);
			List<Address> addressList = sessionCustomerList.get(0).getAddresses();
			boolean addressExist= false;
			if(null!= addressList) {
				for(Address add: addressList) {
					if(address.getAddressId() == add.getAddressId()) {
						addressExist = true;
						address = add;
						break;
					}
				}
			} else {
				addressList = new ArrayList<Address>();
			}
			if(!addressExist) {
				address.setCustomer(sessionCustomerList.get(0));
				addressList.add(address);
			}
		}
		orderDetails.setCustomer(customer);
		orderDetails.setDeliveryAddress(address);
		orderDetails.setDeliverySlotSelected(slot);
		Customer custInfo = orderDetails.getCustomer();
				
		List<OrderItems> orderItems = orderDetails.getOrderItems();
		List<OrderToppings> toppings = orderDetails.getOrderToppings();
		List<ItemVo> itemList = convertOrderItemToItemVo(orderItems);
		List<ToppingVo> toppingsVo = convertOrderToppingToToppingVo(toppings);
		AddressVo addressVo = convertAddressToAddressVo(orderDetails.getDeliveryAddress());
		addressVo.setFirstName(custInfo.getCustomerFirstName());
		addressVo.setLastName(custInfo.getCustomerLastName());
		OrderVo orderVo= new OrderVo();
		orderVo.setOrderId(orderDetails.getOrderId());
		orderVo.setItemList(itemList);
		orderVo.setToppingList(toppingsVo);
		orderVo.setDeliveryAddress(addressVo);
		String deliverySlot = "";
		if(null !=slot) {
			deliverySlot = slot.getStartTime()+"-"+slot.getEndTime();
		}
		orderVo.setDeliverySlot(deliverySlot);
		orderVo.setContactNo(custInfo.getContactNo1());
		session.getTransaction().commit();
		session.close();
		return orderVo;
	}
	
	private List<ItemVo> convertOrderItemToItemVo(List<OrderItems> orderItems) {
		List<ItemVo> itemVoList = new ArrayList<ItemVo>();
		if(null != orderItems) {
			for(OrderItems orderItem: orderItems) {
				Item item=orderItem.getItem();
			
				List<String> itemDetailList = null;
				String itemDetailString = null;			
				ItemVo itemVo = new ItemVo();
				itemVo.setItemId(item.getItemId());
				itemVo.setItemName(item.getItemName());
				itemVo.setItemPrice(item.getItemPrice());
				itemVo.setItemQuantity(orderItem.getQuantity());
				itemDetailList =  new ArrayList<String>();
				itemDetailString = item.getItemDetails();
				String[] itemDetailArray = itemDetailString.split(",");
				for(String itemDetail:itemDetailArray) {
					itemDetailList.add(itemDetail);
				}
				itemVo.setItemDetails(itemDetailList);
				itemVoList.add(itemVo);
			}
		}
		
		return itemVoList;
	}
	private List<ToppingVo> convertOrderToppingToToppingVo(List<OrderToppings> orderToppings) {
		List<ToppingVo> toppingVoList = new ArrayList<ToppingVo>();
		if(null != orderToppings) {
			ToppingVo topping = null;
			AvailableTopping selectedTopping = null;
			for(OrderToppings orderTopping: orderToppings) {
				selectedTopping = orderTopping.getTopping();
				topping =  new ToppingVo();
				topping.setToppingId(selectedTopping.getToppingId());
				topping.setToppingName(selectedTopping.getToppingName());
				topping.setQuantity(orderTopping.getQuantity());
				topping.setPrice(selectedTopping.getToppingPrice());
				toppingVoList.add(topping);
			}
		}
		return toppingVoList;
	}
	private AddressVo convertAddressToAddressVo(Address address) {
		AddressVo addressVo = null;
		if(null != address) {
			
			addressVo = new AddressVo();
			addressVo.setAddressId(address.getAddressId());
			addressVo.setAddressLine1(address.getAddressLine1());
			addressVo.setAddressline2(address.getAddressLine2());
			addressVo.setLandmark(address.getLandmark());
			addressVo.setCity(address.getCity());
			addressVo.setState(address.getState());
			addressVo.setZipcode(""+address.getZipcode());
			
		}
		return addressVo;
	}
}
