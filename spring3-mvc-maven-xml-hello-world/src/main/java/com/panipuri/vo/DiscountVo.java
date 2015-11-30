package com.panipuri.vo;

import java.util.Date;

import com.test.hibernate.DiscountOn;

public class DiscountVo {
	private long discountId;
	private String couponCode;
	private Date startDate;
	private Date endDate;
	private DiscountOn discountCategory;
	private float discountPercentage;
	private boolean oncePerUser;
	private String discountCondition;
	private String discountConditionValue;
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
	 * @return the discountCategory
	 */
	public DiscountOn getDiscountCategory() {
		return discountCategory;
	}
	/**
	 * @param discountCategory the discountCategory to set
	 */
	public void setDiscountCategory(DiscountOn discountCategory) {
		this.discountCategory = discountCategory;
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
	 * @return the discountCondition
	 */
	public String getDiscountCondition() {
		return discountCondition;
	}
	/**
	 * @param discountCondition the discountCondition to set
	 */
	public void setDiscountCondition(String discountCondition) {
		this.discountCondition = discountCondition;
	}
	/**
	 * @return the discountConditionValue
	 */
	public String getDiscountConditionValue() {
		return discountConditionValue;
	}
	/**
	 * @param discountConditionValue the discountConditionValue to set
	 */
	public void setDiscountConditionValue(String discountConditionValue) {
		this.discountConditionValue = discountConditionValue;
	}
}
