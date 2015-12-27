package com.panipuri.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.panipuri.vo.DeliverySlotVo;
import com.panipuri.vo.ItemVo;
import com.panipuri.vo.OrderVo;
import com.panipuri.vo.ToppingVo;
import com.test.hibernate.ComboItemQuantity;
import com.test.hibernate.DeliverySlot;
import com.test.hibernate.DeliverySlotStock;
import com.test.hibernate.dao.DeliverySlotDaoImpl;
import com.test.hibernate.dao.OderDaoImpl;

@Component
public class DeliveryDetailsService {
	@Autowired DeliverySlotDaoImpl deliverySlotDaoImpl;
	@Autowired OderDaoImpl orderDaoImpl;
	public List<DeliverySlotVo> fetchDeliverySlots(String areaId, String orderId) throws Exception {
		List<DeliverySlotVo> deliverySlotsList = new ArrayList<DeliverySlotVo>();
		List<DeliverySlotVo> fullDeliverySlotsList = new ArrayList<DeliverySlotVo>();
		List<DeliverySlot> deliverySlots = deliverySlotDaoImpl.getAvailableDeliverySlots(areaId);
		OrderVo orderInfo = orderDaoImpl.getOrder(orderId);
		List<ItemVo> itemList =orderInfo.getItemList();
		List<ToppingVo> stuffingList =orderInfo.getToppingList();
		if(null !=deliverySlots && !deliverySlots.isEmpty()) {
			Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("IST"));
			DeliverySlotVo deliverySlotVo = null;
			String startTime =null;
			String deliverySlotTime = null;
			System.out.println(orderId);
			for(DeliverySlot deliverySlot : deliverySlots) {
				List<DeliverySlotStock> stockList = deliverySlot.getDeliveryStockList();
				boolean isValidSlot = false;
			
				System.out.println(deliverySlot.getDeliveryStockList().size());
				outerloopItem:
				for(ItemVo item :itemList) {
					for(DeliverySlotStock stock :stockList) {
						if(!item.isComboItem()){
							if(stock.getId() == item.getItemId() && !stock.isStuffing()) {
								if(stock.getQuantity()>=item.getItemQuantity()) {
									isValidSlot = true;
									break;
								} else {
									isValidSlot = false;
									break outerloopItem;
								}
								
							}
						} else {
							if(null !=item.getComboQuantityList()) {
								List<ComboItemQuantity> comboItems = item.getComboQuantityList();
								for(ComboItemQuantity comboItem:comboItems) {
									if(stock.getId() == comboItem.getComboItem().getItemId() && !stock.isStuffing()) {
										int comboItemQuantity = comboItem.getQuantity() * item.getItemQuantity();
										if(stock.getQuantity()>=comboItemQuantity) {
											isValidSlot = true;
											break;
										} else {
											isValidSlot = false;
											break outerloopItem;
										}
										
									}
								}
							}
						}
					}
					
					
				}
				if(isValidSlot) {
					outerloopStuffing:
					for(ToppingVo stuffing :stuffingList) {
						for(DeliverySlotStock stock :stockList) {
							if(stock.getId() == stuffing.getToppingId() && stock.isStuffing()) {
								if(stock.getQuantity()>=stuffing.getQuantity()) {
									isValidSlot = true;
									break;
								} else {
									isValidSlot = false;
									break outerloopStuffing;
								}
							
							}
						}
						
						
					}
				}
				if(isValidSlot) {
					int hours = calendar.get(Calendar.HOUR_OF_DAY);
					int minutes = calendar.get(Calendar.MINUTE);
					startTime = deliverySlot.getStartTime();
					deliverySlotTime = startTime.substring(0, startTime.indexOf("PM"));
					int slotTime = Integer.parseInt(deliverySlotTime)+12;
					//if(hours < slotTime || ((slotTime-hours)==1 && minutes<50)) {
						deliverySlotVo = new DeliverySlotVo();
						deliverySlotVo.setDeliverySlotId(deliverySlot.getDeliverySlotId());
						deliverySlotVo.setEndTime(deliverySlot.getEndTime());
						deliverySlotVo.setStartTime(deliverySlot.getStartTime());
						deliverySlotVo.setSlotQuantity(deliverySlot.getTodaySlotQuantity());
						if(deliverySlot.getTodaySlotQuantity()>0) {
							
							deliverySlotsList.add(deliverySlotVo);
						} else {
							fullDeliverySlotsList.add(deliverySlotVo);
						}
						
					//}
				}
				//}
			}
		} else {
			Exception e = new Exception("ERR_NO_SLOT");
			throw e;
		}
 		return deliverySlotsList;
	}
	/**
	 * @param deliverySlotDaoImpl the deliverySlotDaoImpl to set
	 */
	public void setDeliverySlotDaoImpl(DeliverySlotDaoImpl deliverySlotDaoImpl) {
		this.deliverySlotDaoImpl = deliverySlotDaoImpl;
	}
}
