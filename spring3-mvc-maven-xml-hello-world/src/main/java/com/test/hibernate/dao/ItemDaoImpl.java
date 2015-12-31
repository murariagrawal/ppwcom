package com.test.hibernate.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.panipuri.vo.ItemVo;
import com.panipuri.vo.ToppingVo;
import com.test.hibernate.AvailableTopping;
import com.test.hibernate.ComboItemQuantity;
import com.test.hibernate.Item;
import com.test.hibernate.OrderItems;
import com.test.hibernate.PartyItemQuantity;

public class ItemDaoImpl {
	private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }
	public void addItem(ItemVo itemVo) {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		Item item = new Item();
		item.setItemDetails(itemVo.getItemDetails().get(0));
		item.setItemName(itemVo.getItemName());
		item.setItemPrice(itemVo.getItemPrice());
		item.setComboItem(itemVo.isComboItem());
		item.setPartyItem(itemVo.isPartyItem());
		if(itemVo.isPartyItem()) {
			
			item.setPartyQuantitylist(itemVo.getPartyQuantitylist());
			for(PartyItemQuantity partyItem:itemVo.getPartyQuantitylist()) {
				partyItem.setItem(item);
			}
		}
		if(itemVo.isComboItem()) {
			for(ComboItemQuantity comboItem :itemVo.getComboQuantityList()) {
				
				comboItem.setComboItem(item);
				
			}
			
			item.setComboQuantityList(itemVo.getComboQuantityList());
		}
		
		session.save(item);
		session.getTransaction().commit();
		session.close();
	}
	
	@SuppressWarnings("unchecked")
	public List<ItemVo> getAllAvailableItems() {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		List<Item> allAvailableItems = (List<Item>)session.createCriteria(Item.class).list();
		List<ItemVo> itemVoList = convertItemToItemVo(allAvailableItems);
		for(ItemVo item:itemVoList) {
			if(item.isComboItem()) {
				item.setComboQuantityList(null);
			}
		}
		session.getTransaction().commit();
		session.close();
		return itemVoList;
	}
	@SuppressWarnings("unchecked")
	public List<ItemVo> getAllComboItems() {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		List<Item> allAvailableItems = (List<Item>)session.createCriteria(Item.class).add(Restrictions.eq("comboItem", true)).list();
		List<ItemVo> itemVoList = convertItemToItemVo(allAvailableItems);
		for(ItemVo itemVo: itemVoList) {
			for(Item item :allAvailableItems) {
				if(item.getItemId() == itemVo.getItemId()) {
					Hibernate.initialize(item.getComboQuantityList());
					itemVo.setComboQuantityList(item.getComboQuantityList());
				}
			}
			
		}
		
		session.getTransaction().commit();
		session.close();
		return itemVoList;
	}
	
	public void updateItemDetails(BigDecimal itemPrice, String itemDetails, long itemId) {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		Item selectedItem = (Item)session.get(Item.class, itemId);
		if(null != selectedItem) {			 
			if(null != itemPrice) {
				selectedItem.setItemPrice(itemPrice);
			}
			if(null != itemDetails && !itemDetails.trim().equals("")) {
				selectedItem.setItemDetails(itemDetails);
			}
			session.update(selectedItem);
		}
		session.getTransaction().commit();
		session.close();
		
	}
	
	public void deleteItem(long itemId) {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		Item selectedItem = (Item)session.get(Item.class, itemId);
		session.delete(selectedItem);
		session.getTransaction().commit();
		session.close();
		
	}
	private List<ItemVo> convertItemToItemVo(List<Item> orderItems) {
		List<ItemVo> itemVoList = new ArrayList<ItemVo>();
		if(null != orderItems) {
			for(Item item: orderItems) {
				List<String> itemDetailList = null;
				String itemDetailString = null;			
				ItemVo itemVo = new ItemVo();
				itemVo.setItemId(item.getItemId());
				itemVo.setItemName(item.getItemName());
				itemVo.setPartyItem(item.isPartyItem());
				itemVo.setComboItem(item.isComboItem());
				if(item.isPartyItem()) {					
					Hibernate.initialize(item.getPartyQuantitylist());
					itemVo.setPartyQuantitylist(item.getPartyQuantitylist());
										
				} else if(item.isComboItem()) {
					itemVo.setItemPrice(item.getItemPrice());
					
				} else {
					itemVo.setItemPrice(item.getItemPrice());
					
				}
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
	
	public void addStuffing(ToppingVo toppingVo) {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		AvailableTopping topping = new AvailableTopping();
		topping.setToppingPrice(toppingVo.getPrice());
		topping.setToppingName(toppingVo.getToppingName());
		
		session.save(topping);
		session.getTransaction().commit();
		session.close();
	}
	
	@SuppressWarnings("unchecked")
	public List<AvailableTopping> getAllAvailableStuffing() {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		List<AvailableTopping> allAvailableStuffing = (List<AvailableTopping>)session.createCriteria(AvailableTopping.class).list();
		session.getTransaction().commit();
		session.close();
		return allAvailableStuffing;
	}
	
	public void updateStuffingDetails(BigDecimal stuffingPrice, long stuffingId) {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		AvailableTopping selectedStuffing = (AvailableTopping)session.get(AvailableTopping.class, stuffingId);
		if(null != selectedStuffing) {			
			if(null != stuffingPrice) {
				selectedStuffing.setToppingPrice(stuffingPrice);
			}			
			session.update(selectedStuffing);
		}
		session.getTransaction().commit();
		session.close();
		
	}
	
	public void deleteStuffing(long stuffingId) {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		AvailableTopping selectedStuffing = (AvailableTopping)session.get(AvailableTopping.class, stuffingId);
		session.delete(selectedStuffing);
		session.getTransaction().commit();
		session.close();
		
	}
}
