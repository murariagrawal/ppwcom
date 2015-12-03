package com.test.hibernate;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="available_zipcodes")
public class AvailableZipcodes {
	
	@Id
	@Column(name="zipcode")
	private long zipcode;
	@OneToMany(fetch = FetchType.EAGER,targetEntity=DeliveryArea.class,cascade= CascadeType.ALL,mappedBy="zipcodes")	
	private List<DeliveryArea> area; 
	
	/**
	 * @return the zipcode
	 */
	public long getZipcode() {
		return zipcode;
	}
	/**
	 * @param zipcode the zipcode to set
	 */
	public void setZipcode(long zipcode) {
		this.zipcode = zipcode;
	}
	/**
	 * @return the area
	 */
	public List<DeliveryArea> getArea() {
		return area;
	}
	/**
	 * @param area the area to set
	 */
	public void setArea(List<DeliveryArea> area) {
		this.area = area;
	}
	
	
}
