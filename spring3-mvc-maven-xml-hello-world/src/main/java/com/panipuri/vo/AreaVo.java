package com.panipuri.vo;

import java.util.List;

public class AreaVo {
	private long deliveryAreaId;
	private String areaName;
	private String subAreaName;
	private String city;
	private String state;
	private List<DeliverySlotVo> deliverySlots;
	private String zipcode;
	private boolean servingIndividual;
	private boolean servingParty;
	/**
	 * @return the deliveryAreaId
	 */
	public long getDeliveryAreaId() {
		return deliveryAreaId;
	}
	/**
	 * @param deliveryAreaId the deliveryAreaId to set
	 */
	public void setDeliveryAreaId(long deliveryAreaId) {
		this.deliveryAreaId = deliveryAreaId;
	}
	/**
	 * @return the areaName
	 */
	public String getAreaName() {
		return areaName;
	}
	/**
	 * @param areaName the areaName to set
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the deliverySlots
	 */
	public List<DeliverySlotVo> getDeliverySlots() {
		return deliverySlots;
	}
	/**
	 * @param deliverySlots the deliverySlots to set
	 */
	public void setDeliverySlots(List<DeliverySlotVo> deliverySlots) {
		this.deliverySlots = deliverySlots;
	}
	/**
	 * @return the zipcode
	 */
	public String getZipcode() {
		return zipcode;
	}
	/**
	 * @param zipcode the zipcode to set
	 */
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	/**
	 * @return the subAreaName
	 */
	public String getSubAreaName() {
		return subAreaName;
	}
	/**
	 * @param subAreaName the subAreaName to set
	 */
	public void setSubAreaName(String subAreaName) {
		this.subAreaName = subAreaName;
	}
	/**
	 * @return the servingIndividual
	 */
	public boolean isServingIndividual() {
		return servingIndividual;
	}
	/**
	 * @param servingIndividual the servingIndividual to set
	 */
	public void setServingIndividual(boolean servingIndividual) {
		this.servingIndividual = servingIndividual;
	}
	/**
	 * @return the servingParty
	 */
	public boolean isServingParty() {
		return servingParty;
	}
	/**
	 * @param servingParty the servingParty to set
	 */
	public void setServingParty(boolean servingParty) {
		this.servingParty = servingParty;
	}
	
}
