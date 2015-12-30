package com.panipuri.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.panipuri.vo.AreaSubAreaVo;
import com.panipuri.vo.ItemVo;
import com.panipuri.vo.StatusVo;
import com.panipuri.vo.ToppingVo;
import com.test.hibernate.AvailableTopping;
import com.test.hibernate.DeliverySlot;
import com.test.hibernate.DeliverySlotStock;
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
	public StatusVo updateItem(ItemVo item) {
		itemDaoImpl.updateItemDetails(item.getItemPrice(),item.getItemDetails().get(0),item.getItemId());
		return new StatusVo();
	}
	public StatusVo addStuffing(ToppingVo topping) {
		itemDaoImpl.addStuffing(topping);
		return new StatusVo();
	}
	public List<AvailableTopping> getAllStuffing() {
		List<AvailableTopping> stuffingList = itemDaoImpl.getAllAvailableStuffing();
		return stuffingList;
	}
	public StatusVo updateStuffing(ToppingVo topping) {
		itemDaoImpl.updateStuffingDetails(topping.getPrice(), topping.getToppingId());
		return new StatusVo();
	}
	public StatusVo deleteStuffing(ToppingVo topping) {
		itemDaoImpl.deleteStuffing(topping.getToppingId());
		return new StatusVo();
	}
	public StatusVo deleteItem(long itemId) {
		itemDaoImpl.deleteItem(itemId);
		return new StatusVo();
	}
	public StatusVo addMasterArea(String areaName, String areaCity, String areaState, List<DeliverySlot> deliverySlots, List<DeliverySlotStock> stockInfo) {
		availableDeliveryAreaDaoImpl.addMasterDeliveryArea(areaName, areaCity , areaState, deliverySlots, stockInfo);
		return new StatusVo();
	}
	public StatusVo addArea(String areaName, String subAreaName, Long zipcode, Long masterAreaId, boolean serving, boolean servingParty) {
		availableDeliveryAreaDaoImpl.addDeliveryArea(areaName, subAreaName , zipcode, masterAreaId, serving, servingParty);
		return new StatusVo();
	}
	public List<AreaSubAreaVo> fetchAllArea(){
		return availableDeliveryAreaDaoImpl.getAllAvailableDeliveryArea();
	}
	public List<MasterDeliveryArea> fetchAllMasterArea(){
		return availableDeliveryAreaDaoImpl.getAllMasterAvailableDeliveryArea();
	}
}
