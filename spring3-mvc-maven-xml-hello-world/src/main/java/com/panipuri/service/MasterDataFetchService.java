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
		List<ItemVo> itemsList = itemDaoImpl.getAllAvailableItems();		
		
		return itemsList;
	}
	public List<ItemVo> fetchAllComboItem() {
		List<ItemVo> itemsList = itemDaoImpl.getAllComboItems();		
		
		return itemsList;
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
