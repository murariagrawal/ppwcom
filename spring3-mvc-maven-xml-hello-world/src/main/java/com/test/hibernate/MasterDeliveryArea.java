package com.test.hibernate;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="master_delivery_area")
public class MasterDeliveryArea {
	@Id
	@Column(name="delivery_area_id")
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private long deliveryAreaId;
	@Column(name="areaName", unique=true)
	private String areaName;
	@Column
	private String city;
	@Column
	private String state;
	@OneToMany(fetch = FetchType.EAGER,targetEntity=DeliverySlot.class,cascade= CascadeType.ALL,mappedBy="deliveryArea")
	private List<DeliverySlot> deliverySlots;
	@OneToMany(targetEntity=AvailableZipcodes.class,cascade= CascadeType.ALL,mappedBy="area")
	private List<AvailableZipcodes> zipcodes;
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
	 * @return the deliverySlots
	 */
	public List<DeliverySlot> getDeliverySlots() {
		return deliverySlots;
	}
	/**
	 * @param deliverySlots the deliverySlots to set
	 */
	public void setDeliverySlots(List<DeliverySlot> deliverySlots) {
		this.deliverySlots = deliverySlots;
	}
	/**
	 * @return the zipcodes
	 */
	public List<AvailableZipcodes> getZipcodes() {
		return zipcodes;
	}
	/**
	 * @param zipcodes the zipcodes to set
	 */
	public void setZipcodes(List<AvailableZipcodes> zipcodes) {
		this.zipcodes = zipcodes;
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
}
