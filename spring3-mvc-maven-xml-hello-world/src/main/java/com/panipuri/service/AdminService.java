package com.panipuri.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.panipuri.vo.ItemVo;
import com.panipuri.vo.StatusVo;
import com.test.hibernate.DeliverySlot;
import com.test.hibernate.MasterDeliveryArea;
import com.test.hibernate.dao.AvailableDeliveryAreaDaoImpl;
import com.test.hibernate.dao.ItemDaoImpl;

@Component
public class AdminService {
	@Autowired
	private ItemDaoImpl itemDaoImpl;
	@Autowired
	private AvailableDeliveryAreaDaoImpl availableDeliveryAreaDaoImpl;
	public StatusVo addItem(ItemVo item) {
		itemDaoImpl.addItem(item);
		return new StatusVo();
	}

	public StatusVo addArea(String areaName, String areaCity, String areaState,  List<Long> zipcodes, List<DeliverySlot> deliverySlots) {
		availableDeliveryAreaDaoImpl.addMasterDeliveryArea(areaName, areaCity , areaState, zipcodes, deliverySlots);
		return new StatusVo();
	}
	public List<MasterDeliveryArea> fetchAllArea(){
		return availableDeliveryAreaDaoImpl.getAllAvailableDeliveryArea();
	}
}
