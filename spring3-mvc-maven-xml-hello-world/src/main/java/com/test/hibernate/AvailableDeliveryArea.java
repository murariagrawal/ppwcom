package com.test.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="available_delivery_area")
public class AvailableDeliveryArea {
	@Column
	private String city;
	@Column
	private String state;
	@Id
	@Column(name="zipcode")
	private long zipcode;
	@OneToOne(fetch=FetchType.EAGER)
	private MasterDeliveryArea area;
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
	public MasterDeliveryArea getArea() {
		return area;
	}
	/**
	 * @param area the area to set
	 */
	public void setArea(MasterDeliveryArea area) {
		this.area = area;
	}
}
