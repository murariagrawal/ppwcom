package com.test.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="discount_condition")
public class DiscountCondition {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private long discountConditionId;
	@Column
	private String discountOn;
	@Column
	private String dicountCondition;
	@Column
	private String dicountConditionValue;
	@Column
	private boolean isLimit;
	@ManyToOne
	@JoinColumn(name="discountId")
	private DiscountInformation discount;
	/**
	 * @return the discountOn
	 */
	public String getDiscountOn() {
		return discountOn;
	}
	/**
	 * @param discountOn the discountOn to set
	 */
	public void setDiscountOn(String discountOn) {
		this.discountOn = discountOn;
	}
	/**
	 * @return the dicountCondition
	 */
	public String getDicountCondition() {
		return dicountCondition;
	}
	/**
	 * @param dicountCondition the dicountCondition to set
	 */
	public void setDicountCondition(String dicountCondition) {
		this.dicountCondition = dicountCondition;
	}
	/**
	 * @return the dicountConditionValue
	 */
	public String getDicountConditionValue() {
		return dicountConditionValue;
	}
	/**
	 * @param dicountConditionValue the dicountConditionValue to set
	 */
	public void setDicountConditionValue(String dicountConditionValue) {
		this.dicountConditionValue = dicountConditionValue;
	}
	/**
	 * @return the isLimit
	 */
	public boolean isLimit() {
		return isLimit;
	}
	/**
	 * @param isLimit the isLimit to set
	 */
	public void setLimit(boolean isLimit) {
		this.isLimit = isLimit;
	}
	/**
	 * @return the discountConditionId
	 */
	public long getDiscountConditionId() {
		return discountConditionId;
	}
	/**
	 * @param discountConditionId the discountConditionId to set
	 */
	public void setDiscountConditionId(long discountConditionId) {
		this.discountConditionId = discountConditionId;
	}
	/**
	 * @return the discount
	 */
	public DiscountInformation getDiscount() {
		return discount;
	}
	/**
	 * @param discount the discount to set
	 */
	public void setDiscount(DiscountInformation discount) {
		this.discount = discount;
	}

}
