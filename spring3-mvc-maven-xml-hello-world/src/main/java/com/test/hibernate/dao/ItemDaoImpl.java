package com.test.hibernate.dao;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.panipuri.vo.ItemVo;
import com.test.hibernate.Item;

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
	
	public void updateItemDetails(String itemName, BigDecimal itemPrice, String itemDetails, long itemId) {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		Item selectedItem = (Item)session.get(Item.class, itemId);
		if(null != selectedItem) {
			if(null != itemName && !itemName.trim().equals("")) {
				selectedItem.setItemName(itemName);
			} 
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
		Session session = this.sessionFactory.getCurrentSession();
		session.beginTransaction();
		Item selectedItem = (Item)session.get(Item.class, itemId);
		session.delete(selectedItem);
		session.getTransaction().commit();
		session.close();
		
	}
}