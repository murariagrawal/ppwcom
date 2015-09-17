package com.test.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="master_delivery_area")
public class MasterDeliveryArea {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private long deliveryAreaId;
	@Column(name="areaName", unique=true)
	private String areaName;
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
}
