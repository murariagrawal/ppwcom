package com.panipuri.service.admin;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.panipuri.vo.AreaSubAreaVo;
import com.panipuri.vo.ItemVo;
import com.panipuri.vo.OrderVo;
import com.panipuri.vo.StatusVo;
import com.panipuri.vo.ToppingVo;
import com.test.hibernate.AvailableTopping;
import com.test.hibernate.DeliverySlot;
import com.test.hibernate.DeliverySlotStock;
import com.test.hibernate.MasterDeliveryArea;
import com.test.hibernate.dao.AvailableDeliveryAreaDaoImpl;
import com.test.hibernate.dao.ItemDaoImpl;
import com.test.hibernate.dao.OderDaoImpl;

@Component
public class AdminService {
	@Autowired
	private OderDaoImpl orderDaoImpl;
	@Autowired
	private ItemDaoImpl itemDaoImpl;
	@Autowired
	private AvailableDeliveryAreaDaoImpl availableDeliveryAreaDaoImpl;

	public StatusVo addItem(ItemVo item) {
		itemDaoImpl.addItem(item);
		return new StatusVo();
	}

	public StatusVo updateItem(ItemVo item) {
		itemDaoImpl.updateItemDetails(item.getItemPrice(), item.getItemDetails().get(0), item.getItemId());
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

	public StatusVo addMasterArea(String areaName, String areaCity, String areaState, List<DeliverySlot> deliverySlots,
			List<DeliverySlotStock> stockInfo) {
		availableDeliveryAreaDaoImpl.addMasterDeliveryArea(areaName, areaCity, areaState, deliverySlots, stockInfo);
		return new StatusVo();
	}

	public StatusVo addArea(String areaName, String subAreaName, Long zipcode, Long masterAreaId, boolean serving,
			boolean servingParty) {
		availableDeliveryAreaDaoImpl.addDeliveryArea(areaName, subAreaName, zipcode, masterAreaId, serving,
				servingParty);
		return new StatusVo();
	}

	public List<AreaSubAreaVo> fetchAllArea() {
		return availableDeliveryAreaDaoImpl.getAllAvailableDeliveryArea();
	}

	public List<MasterDeliveryArea> fetchAllMasterArea() {
		return availableDeliveryAreaDaoImpl.getAllMasterAvailableDeliveryArea();
	}

	public MasterDeliveryArea fetchMasterArea(String masterAreaId) {
		return availableDeliveryAreaDaoImpl.getMasterDeliveryArea(masterAreaId);
	}

	public List<OrderVo> fetchOrdersInArea(String masterAreaId) {
		return orderDaoImpl.getOrdersForAnArea(masterAreaId);
	}

	public void addStockToArea(String masterAreaId, int slotStartTime, List<DeliverySlotStock> stockInfo) {
		MasterDeliveryArea area = fetchMasterArea(masterAreaId);

		List<DeliverySlot> slots = area.getDeliverySlots();
		List<DeliverySlotStock> slotStocks = null;
		LinkedHashMap<Integer, LinkedHashMap<Integer, Integer>> stuffingLaterOrder = new LinkedHashMap<Integer, LinkedHashMap<Integer, Integer>>();
		LinkedHashMap<Integer, LinkedHashMap<Integer, Integer>> itemLaterOrder = new LinkedHashMap<Integer, LinkedHashMap<Integer, Integer>>();
		LinkedHashMap<Integer, LinkedHashMap<Integer, Integer>> stuffingInitialOrder = new LinkedHashMap<Integer, LinkedHashMap<Integer, Integer>>();
		LinkedHashMap<Integer, LinkedHashMap<Integer, Integer>> itemInitialOrder = new LinkedHashMap<Integer, LinkedHashMap<Integer, Integer>>();

		for (DeliverySlot slot : slots) {
			slotStocks = slot.getDeliveryStockList();
			if (slotStartTime > slot.getStartTimeNum()) {
				
				for (DeliverySlotStock slotStock : slotStocks) {
					if (!slotStock.isStuffing()) {
						deriveIntialOrderQuantity(itemInitialOrder, slotStock, slotStock.getInitialQuantity());
					} else {
						deriveIntialOrderQuantity(stuffingInitialOrder, slotStock, slotStock.getInitialQuantity());
					}

				}
			} else {
				for (DeliverySlotStock slotStock : slotStocks) {
					int intialQuantity = 0;
					for(DeliverySlotStock areaStock:area.getDeliveryStockList()) {
						if(areaStock.getId() == slotStock.getId() && areaStock.isStuffing() == slotStock.isStuffing()) {
							intialQuantity = areaStock.getInitialQuantity();
							break;
						}
					}
					if (!slotStock.isStuffing()) {
						deriveIntialOrderQuantity(itemLaterOrder, slotStock,intialQuantity);
					} else {
						deriveIntialOrderQuantity(stuffingLaterOrder, slotStock,intialQuantity);
					}

				}
			}

		}
		LinkedHashMap<Long, Integer> itemMaxQuantity = new LinkedHashMap<Long, Integer>();
		LinkedHashMap<Long, Integer> stuffingMaxQuantity = new LinkedHashMap<Long, Integer>();
		List<DeliverySlotStock> stockListTobeAddedPrevious = new ArrayList<DeliverySlotStock>();
		List<DeliverySlotStock> stockListTobeAddedLater = new ArrayList<DeliverySlotStock>();
		for (DeliverySlotStock addedStock : stockInfo) {
			if (addedStock.getQuantity() > 0) {
				for (DeliverySlot slot : slots) {
					if (slotStartTime > slot.getStartTimeNum()) {
						deriveStockTobeAdded(stuffingInitialOrder, itemInitialOrder, stockListTobeAddedPrevious,
								addedStock, slot, itemMaxQuantity, stuffingMaxQuantity);
					} else {
						deriveStockTobeAdded(stuffingLaterOrder, itemLaterOrder, stockListTobeAddedLater,
								addedStock, slot, itemMaxQuantity, stuffingMaxQuantity);						
					}
				}

			}
		}
		availableDeliveryAreaDaoImpl.updateStockList(stockListTobeAddedPrevious, stockListTobeAddedLater, masterAreaId, itemMaxQuantity,stuffingMaxQuantity);

	}

	public void deriveIntialOrderQuantity(LinkedHashMap<Integer, LinkedHashMap<Integer, Integer>> itemInitialOrder,
			DeliverySlotStock slotStock, Integer initialQuantity) {
		LinkedHashMap<Integer, Integer> intialQuantityOrderd;
		int quantityOrdered;
		if (null != itemInitialOrder.get(Integer.parseInt(String.valueOf(slotStock.getId())))) {
			intialQuantityOrderd = itemInitialOrder
					.get(Integer.parseInt(String.valueOf(slotStock.getId())));
			if (null != intialQuantityOrderd.get(initialQuantity)) {
				quantityOrdered = intialQuantityOrderd.get(initialQuantity);
				quantityOrdered = quantityOrdered + slotStock.getQuantityOrdered();
				intialQuantityOrderd.put(initialQuantity, quantityOrdered);
			} else {
				int previousOrderQuantity = 0;
				for (Integer initialQuant : intialQuantityOrderd.keySet()) {
					if (initialQuant < slotStock.getInitialQuantity()) {
						previousOrderQuantity = previousOrderQuantity
								+ intialQuantityOrderd.get(initialQuant);

					}
				}
				previousOrderQuantity = previousOrderQuantity + slotStock.getQuantityOrdered();
				intialQuantityOrderd.put(initialQuantity, previousOrderQuantity);

			}
		} else {
			intialQuantityOrderd = new LinkedHashMap<Integer, Integer>();
			intialQuantityOrderd.put(slotStock.getInitialQuantity(), slotStock.getQuantityOrdered());
			itemInitialOrder.put(Integer.parseInt(String.valueOf(slotStock.getId())),
					intialQuantityOrderd);
		}
	}

	private void deriveStockTobeAdded(LinkedHashMap<Integer, LinkedHashMap<Integer, Integer>> stuffingInitialOrder,
			LinkedHashMap<Integer, LinkedHashMap<Integer, Integer>> itemInitialOrder,
			List<DeliverySlotStock> stockListTobeAddedPrevious, DeliverySlotStock addedStock, DeliverySlot slot, LinkedHashMap<Long, Integer> itemMaxQuantity,
			LinkedHashMap<Long, Integer> stuffingMaxQuantity) {
		LinkedHashMap<Integer, Integer> intialQuantityOrderd;
		DeliverySlotStock stockAdded= null;
		if (!addedStock.isStuffing()) {
			intialQuantityOrderd = itemInitialOrder.get(Integer.parseInt(""+addedStock.getId()));
			Set<Integer> intialQuantitySet = intialQuantityOrderd.keySet();
			for (Integer initialQuant : intialQuantitySet) {
				for (DeliverySlotStock slotStock : slot.getDeliveryStockList()) {
					if (!slotStock.isStuffing()) {
						if (slotStock.getId() == addedStock.getId()
								&& slotStock.getInitialQuantity() == initialQuant) {
							int maxQuantity = 0;
							
							stockAdded = new DeliverySlotStock();
							stockAdded.setSlot(slot);
							stockAdded.setId(addedStock.getId());
							stockAdded.setStuffing(false);
							int balanceQuantity = initialQuant - intialQuantityOrderd.get(initialQuant)
									- slotStock.getQuantity();
							if (balanceQuantity >= addedStock.getQuantity()) {
								// add only added stock quantity
								stockAdded.setQuantity(addedStock.getQuantity());

							} else if (balanceQuantity < addedStock.getQuantity()) {
								// add only balance quantity
								stockAdded.setQuantity(balanceQuantity);
							}
							if(null != itemMaxQuantity.get(slotStock.getId())) {
								maxQuantity = itemMaxQuantity.get(slotStock.getId());
								if(stockAdded.getQuantity() >maxQuantity) {
									itemMaxQuantity.put(slotStock.getId(), stockAdded.getQuantity());
								}
							} else {
								itemMaxQuantity.put(slotStock.getId(), stockAdded.getQuantity());
							}
							stockAdded.setInitialQuantity(addedStock.getQuantity());
							stockListTobeAddedPrevious.add(stockAdded);
						}
					}
				}
			}
		} else {
			intialQuantityOrderd = stuffingInitialOrder.get(Integer.parseInt(""+addedStock.getId()));
			Set<Integer> intialQuantitySet = intialQuantityOrderd.keySet();
			for (Integer initialQuant : intialQuantitySet) {

				for (DeliverySlotStock slotStock : slot.getDeliveryStockList()) {
					if (slotStock.isStuffing()) {
						if (slotStock.getId() == addedStock.getId()
								&& slotStock.getInitialQuantity() == initialQuant) {
							int maxQuantity = 0;
							stockAdded = new DeliverySlotStock();
							stockAdded.setSlot(slot);
							stockAdded.setId(addedStock.getId());
							stockAdded.setStuffing(true);
							int balanceQuantity = initialQuant - intialQuantityOrderd.get(initialQuant)
									- slotStock.getQuantity();
							if (balanceQuantity >= addedStock.getQuantity()) {
								// add only added stock quantity
								stockAdded.setQuantity(addedStock.getQuantity());
							} else if (balanceQuantity < addedStock.getQuantity()) {
								// add only balance quantity
								stockAdded.setQuantity(balanceQuantity);
							}
							if(null != stuffingMaxQuantity.get(slotStock.getId())) {
								maxQuantity = stuffingMaxQuantity.get(slotStock.getId());
								if(stockAdded.getQuantity() >maxQuantity) {
									stuffingMaxQuantity.put(slotStock.getId(), stockAdded.getQuantity());
								}
							} else {
								stuffingMaxQuantity.put(slotStock.getId(), stockAdded.getQuantity());
							}
							stockAdded.setInitialQuantity(addedStock.getQuantity());
							stockListTobeAddedPrevious.add(stockAdded);
						}
					}
				}

			}
		}
	}
}
