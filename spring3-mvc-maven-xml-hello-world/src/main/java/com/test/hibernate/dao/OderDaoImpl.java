package com.test.hibernate.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.panipuri.vo.AddressVo;
import com.panipuri.vo.AreaVo;
import com.panipuri.vo.ItemVo;
import com.panipuri.vo.OrderVo;
import com.panipuri.vo.ToppingVo;
import com.test.hibernate.Address;
import com.test.hibernate.AvailableTopping;
import com.test.hibernate.CashInvoice;
import com.test.hibernate.Customer;
import com.test.hibernate.DeliveryArea;
import com.test.hibernate.DeliverySlot;
import com.test.hibernate.Item;
import com.test.hibernate.Order;
import com.test.hibernate.OrderItems;
import com.test.hibernate.OrderToppings;
import com.test.hibernate.PartyItemQuantity;
import com.test.hibernate.PaymentMode;
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
		order.setStatus(Status.INITIATED.name());
		
		session.save(order);
		session.getTransaction().commit();
		session.close();
		return order.getOrderId();
	}
	public OrderVo getOrderDetails(String orderId, Address address, Customer customer, String slotId) {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		Long areaId = address.getArea().getDeliveryAreaId();
		DeliveryArea area = (DeliveryArea)session.get(DeliveryArea.class, areaId);
		DeliverySlot slot = (DeliverySlot)session.get(DeliverySlot.class, new Long(slotId));
		Order orderDetails = (Order)session.get(Order.class, new Long(orderId));
		List<Customer> sessionCustomerList=(List<Customer>)session.createCriteria(Customer.class).add(Restrictions.eq("contactNo1", customer.getContactNo1())).list();
		if(null == sessionCustomerList || sessionCustomerList.isEmpty()) {
			address.setCustomer(customer);
			address.setArea(area);
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
				address.setArea(area);
				address.setCustomer(sessionCustomerList.get(0));
				addressList.add(address);
			}
		}
		orderDetails.setCustomer(customer);
		orderDetails.setDeliveryAddress(address);
		orderDetails.setDeliverySlotSelected(slot);
		OrderVo orderVo = createOrderVO(orderDetails);
		session.getTransaction().commit();
		session.close();
		return orderVo;
	}
	public OrderVo getOrder(String orderId) {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		Order orderDetails = (Order)session.get(Order.class, new Long(orderId));		
		OrderVo orderVo = createOrderVO(orderDetails);
		session.getTransaction().commit();
		session.close();
		return orderVo;
	}
	
	public OrderVo confirmOrder(String orderId) {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		Order orderDetails = (Order)session.get(Order.class, new Long(orderId));
		orderDetails.setPaymentMode(PaymentMode.COD.name());
		orderDetails.setStatus(Status.ACCEPTED.name());
		int todaySlotQuantity = orderDetails.getDeliverySlotSelected().getTodaySlotQuantity();
		if(todaySlotQuantity > 0) {
			todaySlotQuantity = todaySlotQuantity-1;
		}
		orderDetails.getDeliverySlotSelected().setTodaySlotQuantity(todaySlotQuantity);
		OrderVo orderVo = createOrderVO(orderDetails);
		session.getTransaction().commit();
		session.close();
		return orderVo;
	}
	private OrderVo createOrderVO(Order orderDetails) {
		Customer custInfo = orderDetails.getCustomer();				
		List<OrderItems> orderItems = orderDetails.getOrderItems();
		List<OrderToppings> toppings = orderDetails.getOrderToppings();
		List<ItemVo> itemList = convertOrderItemToItemVo(orderItems);
		List<ToppingVo> toppingsVo = convertOrderToppingToToppingVo(toppings);
		DeliverySlot slot = orderDetails.getDeliverySlotSelected();
		AddressVo addressVo = convertAddressToAddressVo(orderDetails.getDeliveryAddress());
		addressVo.setFirstName(custInfo.getCustomerFirstName());
		addressVo.setLastName(custInfo.getCustomerLastName());
		OrderVo orderVo= new OrderVo();
		String deliverySlot = "";
		if(null !=slot) {
			deliverySlot = slot.getStartTime()+"-"+slot.getEndTime();
		}
		orderVo.setDeliverySlot(deliverySlot);
		orderVo.setOrderId(orderDetails.getOrderId());
		orderVo.setItemList(itemList);
		orderVo.setToppingList(toppingsVo);
		orderVo.setDeliveryAddress(addressVo);
		orderVo.setContactNo(custInfo.getContactNo1());
		return orderVo;
	}
	public OrderVo updateDeliveryStatus(String orderId) {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		Order orderDetails = (Order)session.get(Order.class, new Long(orderId));
		
		orderDetails.setStatus(Status.DELIVERED.name());
		CashInvoice invoice = new CashInvoice();
		invoice.setOrder(orderDetails);
		OrderVo orderVo = createOrderVO(orderDetails);
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
				itemVo.setPartyItem(item.isPartyItem());
				if(item.isPartyItem()) {					
					List<PartyItemQuantity> quantityList = item.getPartyQuantitylist();
					for(PartyItemQuantity partyQuantity :quantityList) {
						if(partyQuantity.getQuantity() == orderItem.getQuantity()) {
							itemVo.setItemPrice(partyQuantity.getPrice());
						}
					}
					
				} else if(item.isComboItem()) {
					itemVo.setComboItem(true);
					itemVo.setComboQuantityList(item.getComboQuantityList());
					itemVo.setItemPrice(item.getItemPrice());
					
				} else {
					itemVo.setItemPrice(item.getItemPrice());
					
				}
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
		AreaVo areaVo = null;
		if(null != address) {
			DeliveryArea area = address.getArea();
			addressVo = new AddressVo();
			addressVo.setAddressId(address.getAddressId());
			addressVo.setAddressLine1(address.getAddressLine1());
			addressVo.setAddressline2(address.getAddressLine2());
			addressVo.setLandmark(address.getLandmark());
			if(area != null) {
				areaVo =  new AreaVo();
				areaVo.setAreaName(area.getAreaName());
				areaVo.setZipcode(String.valueOf(area.getZipcodes().getZipcode()));
				areaVo.setSubAreaName(area.getSubAreaName());
				areaVo.setDeliveryAreaId(area.getDeliveryAreaId());
			}
			addressVo.setArea(areaVo);
			
		}
		return addressVo;
	}
}
