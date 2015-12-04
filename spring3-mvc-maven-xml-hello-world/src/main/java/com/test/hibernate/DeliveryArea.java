package com.test.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="delivery_area")
public class DeliveryArea {
	@Id
	@Column(name="area_id")
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private long deliveryAreaId;
	@Column(name="areaName")
	private String areaName;
	@Column(name="subAreaName", unique=true)
	private String subAreaName;
	@ManyToOne(fetch= FetchType.EAGER)
	@JoinColumn(name="zipcode")
	private AvailableZipcodes zipcodes;
	@Column
	private boolean serving;
	@Column
	private boolean servingParty;
	@ManyToOne(fetch= FetchType.EAGER)
	@JoinColumn(name="delivery_area_id")
	private MasterDeliveryArea masterArea;
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
	 * @return the zipcodes
	 */
	public AvailableZipcodes getZipcodes() {
		return zipcodes;
	}
	/**
	 * @param zipcodes the zipcodes to set
	 */
	public void setZipcodes(AvailableZipcodes zipcodes) {
		this.zipcodes = zipcodes;
	}
	/**
	 * @return the serving
	 */
	public boolean isServing() {
		return serving;
	}
	/**
	 * @param serving the serving to set
	 */
	public void setServing(boolean serving) {
		this.serving = serving;
	}
	/**
	 * @return the masterArea
	 */
	public MasterDeliveryArea getMasterArea() {
		return masterArea;
	}
	/**
	 * @param masterArea the masterArea to set
	 */
	public void setMasterArea(MasterDeliveryArea masterArea) {
		this.masterArea = masterArea;
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
