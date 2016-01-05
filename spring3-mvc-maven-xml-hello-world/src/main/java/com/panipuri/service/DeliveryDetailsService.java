package com.panipuri.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.panipuri.vo.DeliverySlotInfoVo;
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
	public DeliverySlotInfoVo fetchDeliverySlots(String areaId, String orderId) throws Exception {
		DeliverySlotInfoVo deliverySlotInfoVo =  new DeliverySlotInfoVo();
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
			int noOfSlotsAvailable= 0;
			
			LinkedHashMap<String, List<String>> unavailbleItemList = new LinkedHashMap<String, List<String>>();
			for(DeliverySlot deliverySlot : deliverySlots) {
				List<DeliverySlotStock> stockList = deliverySlot.getDeliveryStockList();
				boolean isValidSlot = false;
				List<String> unavailableItems = new ArrayList<String>();
				List<String> unavailableStuffings = new ArrayList<String>();
				
				if(null != itemList) {
					for(ItemVo item :itemList) {
						innerLoop:
						for(DeliverySlotStock stock :stockList) {
							if(!item.isComboItem()){
								if(stock.getId() == item.getItemId() && !stock.isStuffing()) {
									if(stock.getQuantity()>=item.getItemQuantity()) {
										break;
									} else {
										unavailableItems.add(item.getItemName());
										break;
									}
									
								}
							} else {
								if(null !=item.getComboQuantityList()) {
									List<ComboItemQuantity> comboItems = item.getComboQuantityList();
									for(ComboItemQuantity comboItem:comboItems) {
										if(stock.getId() == comboItem.getComboItem().getItemId() && !stock.isStuffing()) {
											int comboItemQuantity = comboItem.getQuantity() * item.getItemQuantity();
											if(stock.getQuantity()>=comboItemQuantity) {											
												break innerLoop;
											} else {
												unavailableItems.add(item.getItemName());											
												break innerLoop;
											}
											
										}
									}
								}
							}
						}
					}
				}
				if(null != stuffingList) {
					for(ToppingVo stuffing :stuffingList) {
						for(DeliverySlotStock stock :stockList) {
							if(stock.getId() == stuffing.getToppingId() && stock.isStuffing()) {
								if(stock.getQuantity()>=stuffing.getQuantity()) {
									break;
								} else {
									unavailableStuffings.add(stuffing.getToppingName());
									break;
								}
							
							}
						}				
					}
				}
				if(unavailableItems.isEmpty() && unavailableStuffings.isEmpty()) {
					
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
				unavailableItems.addAll(unavailableStuffings);
				unavailbleItemList.put(deliverySlot.getStartTime()+"-"+deliverySlot.getEndTime(), unavailableItems);
				
				//}
			}
			deliverySlotInfoVo.setAvailableDeliverySlotList(deliverySlotsList);
			deliverySlotInfoVo.setFullDeliverySlotsList(fullDeliverySlotsList);
			deliverySlotInfoVo.setUnavailbleItemList(unavailbleItemList);
			if(deliverySlotsList.isEmpty() && !fullDeliverySlotsList.isEmpty() ) {
				
			} else if(deliverySlotsList.isEmpty() && !unavailbleItemList.isEmpty()) {
				
			}
			if(noOfSlotsAvailable == 0) {
				
			}
		} else {
			Exception e = new Exception("ERR_NO_SLOT");
			throw e;
		}
 		return deliverySlotInfoVo;
	}
	/**
	 * @param deliverySlotDaoImpl the deliverySlotDaoImpl to set
	 */
	public void setDeliverySlotDaoImpl(DeliverySlotDaoImpl deliverySlotDaoImpl) {
		this.deliverySlotDaoImpl = deliverySlotDaoImpl;
	}
}
