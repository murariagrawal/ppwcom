package com.test.hibernate.dao;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.test.hibernate.Item;

public class ItemDaoImpl extends HibernateDaoSupport{
	public void addItem(String itemName, BigDecimal itemPrice, String itemDetails) {
		Item item = new Item();
		item.setItemDetails(itemDetails);
		item.setItemName(itemName);
		item.setItemPrice(itemPrice);
		getHibernateTemplate().save(item);
	}
	
	@SuppressWarnings("unchecked")
	public List<Item> getAllAvailableItems() {
		DetachedCriteria allAvailableItemsCriteria = DetachedCriteria.forClass(Item.class);
		List<Item> allAvailableItems = getHibernateTemplate().findByCriteria(allAvailableItemsCriteria);
		return allAvailableItems;
	}
	
	public void updateItemDetails(String itemName, BigDecimal itemPrice, String itemDetails, long itemId) {
		Item selectedItem = (Item)getHibernateTemplate().get(Item.class, itemId);
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
			getHibernateTemplate().update(selectedItem);
		}
		
	}
	
	public void deleteItem(long itemId) {
		Item selectedItem = (Item)getHibernateTemplate().get(Item.class, itemId);
		getHibernateTemplate().delete(selectedItem);
		
	}
}
