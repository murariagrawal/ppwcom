package com.panipuri.vo;

import java.util.List;
import java.util.Set;

public class AreaSubAreaVo {
	private long deliveryAreaId;
	private String areaName;
	private List<AreaVo> subArea;
	private Set<String> zipcodes;
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
	 * @return the subArea
	 */
	public List<AreaVo> getSubArea() {
		return subArea;
	}
	/**
	 * @param subArea the subArea to set
	 */
	public void setSubArea(List<AreaVo> subArea) {
		this.subArea = subArea;
	}
	/**
	 * @return the zipcodes
	 */
	public Set<String> getZipcodes() {
		return zipcodes;
	}
	/**
	 * @param zipcodes the zipcodes to set
	 */
	public void setZipcodes(Set<String> zipcodes) {
		this.zipcodes = zipcodes;
	}
	
	
}
