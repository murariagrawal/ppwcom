package com.test.hibernate;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="master_delivery_area")
public class MasterDeliveryArea {
	@Id
	@Column(name="delivery_area_id")
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private long deliveryAreaId;
	@Column(name="areaName")
	private String areaName;
	@Column(name="subAreaName", unique=true)
	private String subAreaName;
	@Column
	private String city;
	@Column
	private String state;
	@OneToMany(fetch = FetchType.EAGER,targetEntity=DeliverySlot.class,cascade= CascadeType.ALL,mappedBy="deliveryArea")
	private List<DeliverySlot> deliverySlots;
	@OneToMany(fetch = FetchType.EAGER,targetEntity=DeliveryArea.class,cascade= CascadeType.ALL,mappedBy="masterArea")
	private List<DeliveryArea> deliveryAreas;
	
	@ManyToOne
	@JoinColumn(name="crew_id")
	private Crew crew;
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
	
}
