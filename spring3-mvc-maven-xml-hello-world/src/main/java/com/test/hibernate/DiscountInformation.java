package com.test.hibernate;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name="discount")
public class DiscountInformation {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long discountId;
	@Column(unique=true)
	private String couponCode;
	@Column
	private Date startDate;
	@Column
	private Date endDate;
	@OneToMany(targetEntity=DiscountCondition.class, mappedBy="discount", cascade=CascadeType.ALL)
	private List<DiscountCondition> discountOn;
	@OneToMany(targetEntity=DiscountCondition.class, mappedBy="discount", cascade=CascadeType.ALL)
	private List<DiscountCondition> discountConditions;
	@Column
	private String discountExceptionMessage;
	
	@Column
	private float discountPercentage;
	@Column
	private boolean oncePerUser;
	/**
	 * @return the discountId
	 */
	public long getDiscountId() {
		return discountId;
	}
	/**
	 * @param discountId the discountId to set
	 */
	public void setDiscountId(long discountId) {
		this.discountId = discountId;
	}
	/**
	 * @return the couponCode
	 */
	public String getCouponCode() {
		return couponCode;
	}
	/**
	 * @param couponCode the couponCode to set
	 */
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	/**
	 * @return the discountPercentage
	 */
	public float getDiscountPercentage() {
		return discountPercentage;
	}
	/**
	 * @param discountPercentage the discountPercentage to set
	 */
	public void setDiscountPercentage(float discountPercentage) {
		this.discountPercentage = discountPercentage;
	}
	/**
	 * @return the discountOn
	 */
	public List<DiscountCondition> getDiscountOn() {
		return discountOn;
	}
	/**
	 * @param discountOn the discountOn to set
	 */
	public void setDiscountOn(List<DiscountCondition> discountOn) {
		this.discountOn = discountOn;
	}
	/**
	 * @return the discountConditions
	 */
	public List<DiscountCondition> getDiscountConditions() {
		return discountConditions;
	}
	/**
	 * @param discountConditions the discountConditions to set
	 */
	public void setDiscountConditions(List<DiscountCondition> discountConditions) {
		this.discountConditions = discountConditions;
	}
	/**
	 * @return the oncePerUser
	 */
	public boolean isOncePerUser() {
		return oncePerUser;
	}
	/**
	 * @param oncePerUser the oncePerUser to set
	 */
	public void setOncePerUser(boolean oncePerUser) {
		this.oncePerUser = oncePerUser;
	}
	/**
	 * @return the discountExceptionMessage
	 */
	public String getDiscountExceptionMessage() {
		return discountExceptionMessage;
	}
	/**
	 * @param discountExceptionMessage the discountExceptionMessage to set
	 */
	public void setDiscountExceptionMessage(String discountExceptionMessage) {
		this.discountExceptionMessage = discountExceptionMessage;
	}
	
}
