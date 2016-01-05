package com.panipuri.vo;

import java.util.LinkedHashMap;
import java.util.List;

public class DeliverySlotInfoVo {
	private List<DeliverySlotVo> fullDeliverySlotsList;
	private List<DeliverySlotVo> availableDeliverySlotList;
	private LinkedHashMap<String, List<String>> unavailbleItemList;
	/**
	 * @return the fullDeliverySlotsList
	 */
	public List<DeliverySlotVo> getFullDeliverySlotsList() {
		return fullDeliverySlotsList;
	}
	/**
	 * @param fullDeliverySlotsList the fullDeliverySlotsList to set
	 */
	public void setFullDeliverySlotsList(List<DeliverySlotVo> fullDeliverySlotsList) {
		this.fullDeliverySlotsList = fullDeliverySlotsList;
	}
	/**
	 * @return the availableDeliverySlotList
	 */
	public List<DeliverySlotVo> getAvailableDeliverySlotList() {
		return availableDeliverySlotList;
	}
	/**
	 * @param availableDeliverySlotList the availableDeliverySlotList to set
	 */
	public void setAvailableDeliverySlotList(List<DeliverySlotVo> availableDeliverySlotList) {
		this.availableDeliverySlotList = availableDeliverySlotList;
	}
	/**
	 * @return the unavailbleItemList
	 */
	public LinkedHashMap<String, List<String>> getUnavailbleItemList() {
		return unavailbleItemList;
	}
	/**
	 * @param unavailbleItemList the unavailbleItemList to set
	 */
	public void setUnavailbleItemList(LinkedHashMap<String, List<String>> unavailbleItemList) {
		this.unavailbleItemList = unavailbleItemList;
	}
}
