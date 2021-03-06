package com.test.hibernate.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.panipuri.service.admin.AdminService;
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
import com.test.hibernate.DeliverySlotStock;
import com.test.hibernate.Item;
import com.test.hibernate.MasterDeliveryArea;
import com.test.hibernate.Order;
import com.test.hibernate.OrderItems;
import com.test.hibernate.OrderToppings;
import com.test.hibernate.PartyItemQuantity;
import com.test.hibernate.PaymentMode;
import com.test.hibernate.Status;

public class OderDaoImpl {
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public OrderVo addOrder(List<ItemVo> itemList, List<ToppingVo> toppingList, String orderId, String phoneNumber, String areaId) {
		Order order = null;
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		if (null != orderId && !orderId.equals("")) {
			order = (Order) session.get(Order.class, new Long(orderId));
		} else {
			order = new Order();
		}
		List<OrderItems> existingOrderItems = order.getOrderItems();
		List<OrderToppings> existingOrderToppings =order.getOrderToppings();
		if(existingOrderItems != null) {
			List<OrderItems> tempListItems = new ArrayList<OrderItems>();
			tempListItems.addAll(existingOrderItems);
			int removedCount = 0;
			for(int i=0;i<tempListItems.size();i++) {
				boolean orderItemExist  = false;
				if (null != itemList) {
					for (ItemVo item : itemList) {
						if(tempListItems.get(i).getItem().getItemId() == item.getItemId()) {
							orderItemExist = true;
							break;
						}
					}
					if(!orderItemExist) {
						existingOrderItems.remove(i-removedCount);
						removedCount = removedCount+1;
					}
				} else {
					existingOrderItems.clear();
				}
			}
		}
		if (null != itemList) {
			for (ItemVo item : itemList) {
				if(existingOrderItems != null) {
					boolean orderItemExist  = false;
					for(OrderItems orderItemexisting:existingOrderItems) {
						if(orderItemexisting.getItem().getItemId() == item.getItemId()) {
							orderItemexisting.setQuantity(item.getItemQuantity());
							orderItemExist = true;
							break;
						}
					}
					if(!orderItemExist) {
						OrderItems orderItem = new OrderItems();
						Item itemInfo = new Item();
						itemInfo.setItemId(item.getItemId());
						orderItem.setItem((Item)session.get(Item.class, item.getItemId()));
						orderItem.setQuantity(item.getItemQuantity());
						orderItem.setOrder(order);
						existingOrderItems.add(orderItem);
					}
				} else {
					existingOrderItems = new ArrayList<OrderItems>();
					OrderItems orderItem = new OrderItems();
					Item itemInfo = new Item();
					itemInfo.setItemId(item.getItemId());
					orderItem.setItem((Item)session.get(Item.class, item.getItemId()));
					orderItem.setQuantity(item.getItemQuantity());
					orderItem.setOrder(order);
					existingOrderItems.add(orderItem);
				}
				

			}
		}
		if(existingOrderToppings != null) {
			List<OrderToppings> tempListTopping = new ArrayList<OrderToppings>();
			tempListTopping.addAll(existingOrderToppings);
			int removedCount = 0;
			for(int i=0;i<tempListTopping.size();i++) {
				boolean orderToppingExist  = false;
				if (null != toppingList) {
					for (ToppingVo topping : toppingList) {
						if(tempListTopping.get(i).getTopping().getToppingId() == topping.getToppingId()) {
							orderToppingExist = true;
							break;
						}
					}
					if(!orderToppingExist) {
						existingOrderToppings.remove(i-removedCount);
						removedCount = removedCount+1;
					}
				} else {
					existingOrderToppings.clear();
				}
			}
		}
		if (null != toppingList) {
			for (ToppingVo topping : toppingList) {
				if(existingOrderToppings != null) {
					boolean orderToppingExist  = false;
					for(OrderToppings ordertoppingexisting:existingOrderToppings) {
						if(ordertoppingexisting.getTopping().getToppingId() == topping.getToppingId()) {
							ordertoppingexisting.setQuantity(topping.getQuantity());
							orderToppingExist = true;
							break;
						}
					}
					if(!orderToppingExist) {
						OrderToppings ordertopping = new OrderToppings();
						AvailableTopping availableTopping = new AvailableTopping();
						availableTopping.setToppingId(topping.getToppingId());						;
						ordertopping.setTopping((AvailableTopping)session.get(AvailableTopping.class, topping.getToppingId()));
						ordertopping.setQuantity(topping.getQuantity());
						ordertopping.setOrder(order);
						existingOrderToppings.add(ordertopping);
					}
				} else {
					existingOrderToppings = new ArrayList<OrderToppings>();
					OrderToppings ordertopping = new OrderToppings();
					AvailableTopping availableTopping = new AvailableTopping();
					availableTopping.setToppingId(topping.getToppingId());
					ordertopping.setTopping((AvailableTopping)session.get(AvailableTopping.class, topping.getToppingId()));
					ordertopping.setQuantity(topping.getQuantity());
					ordertopping.setOrder(order);
					existingOrderToppings.add(ordertopping);
				}
				
			}
		}
		
		if(phoneNumber !=  null && !phoneNumber.equals("")) {
			Customer cust = null;
			List<Customer> sessionCustomerList = (List<Customer>) session.createCriteria(Customer.class)
					.add(Restrictions.eq("contactNo1", phoneNumber)).list();
			if (null == sessionCustomerList || sessionCustomerList.isEmpty()) {
				cust = new Customer();
				cust.setContactNo1(phoneNumber);
				session.save(cust);
			} else {
				cust = sessionCustomerList.get(0);
			}
			order.setCustomer(cust);
		}
		
		
		order.setOrderItems(existingOrderItems);
		order.setOrderToppings(existingOrderToppings);
		order.setStatus(Status.INITIATED.name());
		
		session.saveOrUpdate(order);
		OrderVo orderVo = createOrderVO(order);
		session.getTransaction().commit();
		session.close();
		
		return orderVo;
	}

	public OrderVo getOrderDetails(String orderId, Address address, Customer customer, String slotId,
			boolean isEditAddress) {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		Long areaId = address.getArea().getDeliveryAreaId();
		DeliveryArea area = (DeliveryArea) session.get(DeliveryArea.class, areaId);
		DeliverySlot slot = null;
		Customer cust = null;
		if (null != slotId && !slotId.equals("")) {
			slot = (DeliverySlot) session.get(DeliverySlot.class, new Long(slotId));
		}
		Order orderDetails = (Order) session.get(Order.class, new Long(orderId));
		List<Customer> sessionCustomerList = (List<Customer>) session.createCriteria(Customer.class)
				.add(Restrictions.eq("contactNo1", customer.getContactNo1())).list();
		if (null == sessionCustomerList || sessionCustomerList.isEmpty()) {
			address.setCustomer(customer);
			address.setArea(area);
			customer.getAddresses().add(address);
			session.saveOrUpdate(customer);
			orderDetails.setCustomer(customer);
		} else {
			cust = sessionCustomerList.get(0);
			cust.setEmailAddress(customer.getEmailAddress());
			List<Address> addressList = sessionCustomerList.get(0).getAddresses();
			boolean addressExist = false;
			if (null != addressList) {
				for (Address add : addressList) {
					if (address.getAddressId() == add.getAddressId()) {
						addressExist = true;
						if (isEditAddress) {
							DeliveryArea updatedArea = (DeliveryArea) session.get(DeliveryArea.class,
									address.getArea().getDeliveryAreaId());
							add.setAddressLine1(address.getAddressLine1());
							add.setAddressLine2(address.getAddressLine2());
							add.setLandmark(address.getLandmark());
							add.setArea(updatedArea);
						}
						address = add;
						break;
					}
				}
			} else {
				addressList = new ArrayList<Address>();
			}
			if (!addressExist) {
				address.setArea(area);
				address.setCustomer(sessionCustomerList.get(0));
				addressList.add(address);
			}
			orderDetails.setCustomer(cust);
		}
		
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
		Order orderDetails = (Order) session.get(Order.class, new Long(orderId));
		OrderVo orderVo = createOrderVO(orderDetails);
		session.getTransaction().commit();
		session.close();
		return orderVo;
	}

	public OrderVo confirmOrder(String orderId) {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		Order orderDetails = (Order) session.get(Order.class, new Long(orderId));
		orderDetails.setPaymentMode(PaymentMode.COD.name());
		orderDetails.setStatus(Status.ACCEPTED.name());
		int todaySlotQuantity = orderDetails.getDeliverySlotSelected().getTodaySlotQuantity();
		if (null != orderDetails.getDeliveryAddress() && null != orderDetails.getDeliveryAddress().getArea()) {
			if (null != orderDetails.getDeliveryAddress().getArea().getMasterArea()
					&& null != orderDetails.getDeliveryAddress().getArea().getMasterArea().getDeliverySlots()) {
				MasterDeliveryArea area = orderDetails.getDeliveryAddress().getArea().getMasterArea();
				List<DeliverySlot> slots = area.getDeliverySlots();
				List<DeliverySlotStock> stockList = null;
				List<OrderItems> orderItems = orderDetails.getOrderItems();
				List<OrderToppings> toppings = orderDetails.getOrderToppings();
				DeliverySlot selectedSlot = orderDetails.getDeliverySlotSelected();
				LinkedHashMap<Integer, LinkedHashMap<Integer, Integer>> stuffingInitialOrder = new LinkedHashMap<Integer, LinkedHashMap<Integer, Integer>>();
				LinkedHashMap<Integer, LinkedHashMap<Integer, Integer>> itemInitialOrder = new LinkedHashMap<Integer, LinkedHashMap<Integer, Integer>>();

				
				for (DeliverySlot slot : slots) {
					stockList = slot.getDeliveryStockList();
					for (DeliverySlotStock stock : stockList) {
						
						if (stock.isStuffing()) {
							for (OrderToppings stuffing : toppings) {
								if(stock.getId() == stuffing.getTopping().getToppingId()) {
									if(selectedSlot.getDeliverySlotId() == slot.getDeliverySlotId()) {
										int finalQuantity = stock.getQuantityOrdered()+stuffing.getQuantity();
										stock.setQuantityOrdered(finalQuantity);
									}									
								}
							}
						} else {
							for (OrderItems item : orderItems) {
								if(stock.getId() == item.getItem().getItemId()) {
									if(selectedSlot.getDeliverySlotId() == slot.getDeliverySlotId()) {
										int finalQuantity = stock.getQuantityOrdered()+item.getQuantity();
										stock.setQuantityOrdered(finalQuantity);
									}
									
									
								}
							}
						}
					}
				}
				AdminService adminService = new AdminService();
				for (DeliverySlot slot : slots) {
					List<DeliverySlotStock> slotStocks = slot.getDeliveryStockList();
					for (DeliverySlotStock slotStock : slotStocks) {
						if (!slotStock.isStuffing()) {
							adminService.deriveIntialOrderQuantity(itemInitialOrder, slotStock, slotStock.getInitialQuantity());
						} else {
							adminService.deriveIntialOrderQuantity(stuffingInitialOrder, slotStock, slotStock.getInitialQuantity());
						}

					}
					
					
				}
				LinkedHashMap<Long, Integer> itemMaxCapacity = new LinkedHashMap<Long, Integer>();
				LinkedHashMap<Long, Integer> stuffingMaxCapacity = new LinkedHashMap<Long, Integer>();
				for(DeliverySlotStock slotStock :selectedSlot.getDeliveryStockList()) {
					if (!slotStock.isStuffing()) {
						if(null != itemInitialOrder.get(Integer.parseInt(""+slotStock.getId()))) {
							Set<Integer> capacitySet = itemInitialOrder.get(Integer.parseInt(""+slotStock.getId())).keySet();
							int maxCapacity = 0;
							for(Integer capacity:capacitySet) {
								if(maxCapacity <capacity) {
									maxCapacity = capacity;
								}
							}
							maxCapacity = maxCapacity-itemInitialOrder.get(Integer.parseInt(""+slotStock.getId())).get(maxCapacity);
							itemMaxCapacity.put(slotStock.getId(), maxCapacity);
						}
					} else {
						if(null != stuffingInitialOrder.get(Integer.parseInt(""+slotStock.getId()))) {
							Set<Integer> capacitySet = stuffingInitialOrder.get(Integer.parseInt(""+slotStock.getId())).keySet();
							int maxCapacity = 0;
							for(Integer capacity:capacitySet) {
								if(maxCapacity <capacity) {
									maxCapacity = capacity;
								}
							}
							maxCapacity = maxCapacity-stuffingInitialOrder.get(Integer.parseInt(""+slotStock.getId())).get(maxCapacity);
							stuffingMaxCapacity.put(slotStock.getId(), maxCapacity);
						}
					}
				}
				
				for (DeliverySlot slot : slots) {
					List<DeliverySlotStock> slotStocks = slot.getDeliveryStockList();
					for (DeliverySlotStock slotStock : slotStocks) {
						
						if (!slotStock.isStuffing()) {
							if(null != itemInitialOrder.get(Integer.parseInt(""+slotStock.getId())) && null !=itemInitialOrder.get(Integer.parseInt(""+slotStock.getId())).get(slotStock.getInitialQuantity())) {
								int finalQuantity = slotStock.getInitialQuantity() - itemInitialOrder.get(Integer.parseInt(""+slotStock.getId())).get(slotStock.getInitialQuantity());
								if(null != itemMaxCapacity.get(slotStock.getId())) {
									if(finalQuantity > itemMaxCapacity.get(slotStock.getId())) {
										finalQuantity = itemMaxCapacity.get(slotStock.getId());
									}
								}
								
								if(finalQuantity<slotStock.getQuantity()) {
									slotStock.setQuantity(finalQuantity);
								}
							}							
						} else {
							if(null != stuffingInitialOrder.get(Integer.parseInt(""+slotStock.getId())) && null !=stuffingInitialOrder.get(Integer.parseInt(""+slotStock.getId())).get(slotStock.getInitialQuantity())) {
								int finalQuantity = slotStock.getInitialQuantity() - stuffingInitialOrder.get(Integer.parseInt(""+slotStock.getId())).get(slotStock.getInitialQuantity());
								if(null != stuffingMaxCapacity.get(slotStock.getId())) {
									if(finalQuantity > stuffingMaxCapacity.get(slotStock.getId())) {
										finalQuantity = stuffingMaxCapacity.get(slotStock.getId());
									}
								}								
								if(finalQuantity<slotStock.getQuantity()) {
									slotStock.setQuantity(finalQuantity);
								}
							}	
						}

					}
					
				}
				for(DeliverySlotStock stock : area.getDeliveryStockList()) {
					if (stock.isStuffing()) {
						for (OrderToppings stuffing : toppings) {
							if(stock.getId() == stuffing.getTopping().getToppingId()) {
								int finalQuantity = stock.getQuantity()-stuffing.getQuantity();
								stock.setQuantity(finalQuantity);
							}
						}
					} else {
						for (OrderItems item : orderItems) {
							if(stock.getId() == item.getItem().getItemId()) {
								int finalQuantity = stock.getQuantity()-item.getQuantity();
								stock.setQuantity(finalQuantity);
							}
						}
					}
					
				}
			}
		}
		;
		if (todaySlotQuantity > 0) {
			todaySlotQuantity = todaySlotQuantity - 1;
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
		OrderVo orderVo = new OrderVo();
		String deliverySlot = "";
		if (null != slot) {
			deliverySlot = slot.getStartTime() + "-" + slot.getEndTime();
		}
		if(null != custInfo) {
			addressVo.setFirstName(custInfo.getCustomerFirstName());
			addressVo.setLastName(custInfo.getCustomerLastName());
			orderVo.setContactNo(custInfo.getContactNo1());
		}
		orderVo.setDeliverySlot(deliverySlot);
		orderVo.setOrderId(orderDetails.getOrderId());
		orderVo.setItemList(itemList);
		orderVo.setToppingList(toppingsVo);
		orderVo.setDeliveryAddress(addressVo);
		
		return orderVo;
	}

	public OrderVo updateDeliveryStatus(String orderId) {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		Order orderDetails = (Order) session.get(Order.class, new Long(orderId));

		orderDetails.setStatus(Status.DELIVERED.name());
		CashInvoice invoice = new CashInvoice();
		invoice.setOrder(orderDetails);
		OrderVo orderVo = createOrderVO(orderDetails);
		session.getTransaction().commit();
		session.close();
		return orderVo;
	}
	public List<OrderVo> getOrdersForAnArea(String areaId) {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		List<Order> orderDetails = (List<Order>) session.createCriteria(Order.class,"order").createAlias("order.deliveryAddress.area.masterArea", "masterArea")
				.add(Restrictions.eq("masterArea.deliveryAreaId",  new Long(areaId))).add(Restrictions.eq("order.status", Status.ACCEPTED.name())).list();
		OrderVo orderVo =null;
		List<OrderVo> orderList = new ArrayList<OrderVo>();
		if(orderDetails != null) {
			for(Order order :orderDetails) {
				 orderVo = createOrderVO(order);
				 orderList.add(orderVo);
			}
		}
		
		session.getTransaction().commit();
		session.close();
		return orderList;
	}

	private List<ItemVo> convertOrderItemToItemVo(List<OrderItems> orderItems) {
		List<ItemVo> itemVoList = new ArrayList<ItemVo>();
		if (null != orderItems) {
			for (OrderItems orderItem : orderItems) {
				Item item = orderItem.getItem();

				List<String> itemDetailList = null;
				String itemDetailString = null;
				ItemVo itemVo = new ItemVo();
				itemVo.setItemId(item.getItemId());
				itemVo.setItemName(item.getItemName());
				itemVo.setPartyItem(item.isPartyItem());
				if (item.isPartyItem()) {
					List<PartyItemQuantity> quantityList = item.getPartyQuantitylist();
					for (PartyItemQuantity partyQuantity : quantityList) {
						if (partyQuantity.getQuantity() == orderItem.getQuantity()) {
							itemVo.setItemPrice(partyQuantity.getPrice());
						}
					}

				} else if (item.isComboItem()) {
					itemVo.setComboItem(true);
					Hibernate.initialize(item.getComboQuantityList());
					itemVo.setComboQuantityList(item.getComboQuantityList());
					itemVo.setItemPrice(item.getItemPrice());

				} else {
					itemVo.setItemPrice(item.getItemPrice());

				}
				itemVo.setItemQuantity(orderItem.getQuantity());
				itemDetailList = new ArrayList<String>();
				itemDetailString = item.getItemDetails();
				String[] itemDetailArray = itemDetailString.split(",");
				for (String itemDetail : itemDetailArray) {
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
		if (null != orderToppings) {
			ToppingVo topping = null;
			AvailableTopping selectedTopping = null;
			for (OrderToppings orderTopping : orderToppings) {
				selectedTopping = orderTopping.getTopping();
				topping = new ToppingVo();
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
		AddressVo addressVo = new AddressVo();
		AreaVo areaVo = null;
		if (null != address) {
			DeliveryArea area = address.getArea();
			addressVo = new AddressVo();
			addressVo.setAddressId(address.getAddressId());
			addressVo.setAddressLine1(address.getAddressLine1());
			addressVo.setAddressline2(address.getAddressLine2());
			addressVo.setLandmark(address.getLandmark());
			if (area != null) {
				areaVo = new AreaVo();
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
