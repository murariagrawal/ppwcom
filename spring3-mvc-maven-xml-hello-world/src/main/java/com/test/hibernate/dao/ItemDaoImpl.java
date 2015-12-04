package com.test.hibernate.dao;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.panipuri.vo.ItemVo;
import com.panipuri.vo.ToppingVo;
import com.test.hibernate.AvailableTopping;
import com.test.hibernate.Item;
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
		if(itemVo.isPartyItem()) {
			item.setPartyItem(itemVo.isPartyItem());
			item.setPartyQuantitylist(itemVo.getPartyQuantitylist());
			for(PartyItemQuantity partyItem:itemVo.getPartyQuantitylist()) {
				partyItem.setItem(item);
			}
		}
		session.save(item);
		session.getTransaction().commit();
		session.close();
	}
	
	@SuppressWarnings("unchecked")
	public List<Item> getAllAvailableItems() {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		List<Item> allAvailableItems = (List<Item>)session.createCriteria(Item.class).list();
		session.getTransaction().commit();
		session.close();
		return allAvailableItems;
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
