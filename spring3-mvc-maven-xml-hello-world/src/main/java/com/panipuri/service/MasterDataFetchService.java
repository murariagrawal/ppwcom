package com.panipuri.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.panipuri.vo.ItemVo;
import com.panipuri.vo.ToppingVo;
import com.test.hibernate.AvailableTopping;
import com.test.hibernate.Item;
import com.test.hibernate.dao.ItemDaoImpl;


@Component
@Lazy
public class MasterDataFetchService {
	@Autowired
	private ItemDaoImpl itemDaoImpl;
	public List<ItemVo> fetchAllAvailableItem() {
		List<Item> itemsList = itemDaoImpl.getAllAvailableItems();		
		List<ItemVo> itemList = new ArrayList<ItemVo>();		
		List<String> itemDetailList = null;
		String itemDetailString = null;		
		if(null != itemsList) {
			for(Item item: itemsList) {
				ItemVo itemVo = new ItemVo();
				itemVo.setItemId(item.getItemId());
				itemVo.setItemName(item.getItemName());
				itemVo.setItemPrice(item.getItemPrice());
				itemDetailList =  new ArrayList<String>();
				itemDetailString = item.getItemDetails();
				String[] itemDetailArray = itemDetailString.split(",");
				for(String itemDetail:itemDetailArray) {
					itemDetailList.add(itemDetail);
				}
				itemVo.setItemDetails(itemDetailList);
				itemList.add(itemVo);
			}
		}		
		return itemList;
	}
	public List<ToppingVo> fetchAllAvailableStuffing() {
		
		List<AvailableTopping> stuffingList = itemDaoImpl.getAllAvailableStuffing();		
		List<ToppingVo> struffingVoList = new ArrayList<ToppingVo>();
		if(null != stuffingList) {
			for(AvailableTopping item: stuffingList) {
				ToppingVo toppingVo = new ToppingVo();
				toppingVo.setToppingId(item.getToppingId());
				toppingVo.setToppingName(item.getToppingName());
				toppingVo.setPrice(item.getToppingPrice());
				
				struffingVoList.add(toppingVo);
			}
		}
		return struffingVoList;
	}
}
