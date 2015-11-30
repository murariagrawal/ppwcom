package com.panipuri.vo;

import java.util.List;

public class CrewVo {
	private long crewId;
	private String name;
	private String address;
	private String contactnumber;
	private long salary;
	private boolean isAvailable;
	private List<AreaVo> area;
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
	public List<AreaVo> getArea() {
		return area;
	}
	/**
	 * @param area the area to set
	 */
	public void setArea(List<AreaVo> area) {
		this.area = area;
	}
	
}
