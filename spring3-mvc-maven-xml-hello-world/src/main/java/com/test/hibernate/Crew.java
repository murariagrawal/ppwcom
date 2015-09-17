package com.test.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="crew")
public class Crew {
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long crewId;
	@Column
	private String name;
	@Column
	private String address;
	@Column(unique=true, nullable=false, length=10)
	private String contactnumber;
	@Column
	private long salary;
	@Column(name="is_available")
	private boolean isAvailable;
	@OneToOne
	private MasterDeliveryArea area;
	
	/**
	 * @return the crewId
	 */
	public long getCrewId() {
		return crewId;
	}
	/**
	 * @param crewId the crewId to set
	 */
	public void setCrewId(long crewId) {
		this.crewId = crewId;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the contactnumber
	 */
	public String getContactnumber() {
		return contactnumber;
	}
	/**
	 * @param contactnumber the contactnumber to set
	 */
	public void setContactnumber(String contactnumber) {
		this.contactnumber = contactnumber;
	}
	/**
	 * @return the salary
	 */
	public long getSalary() {
		return salary;
	}
	/**
	 * @param salary the salary to set
	 */
	public void setSalary(long salary) {
		this.salary = salary;
	}
	/**
	 * @return the isAvailable
	 */
	public boolean isAvailable() {
		return isAvailable;
	}
	/**
	 * @param isAvailable the isAvailable to set
	 */
	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
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
