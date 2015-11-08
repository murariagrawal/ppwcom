package com.test.hibernate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="OneTimePassword")
public class OneTimePassword {
	@OneToOne(targetEntity = Order.class)
	private Order order;
	@EmbeddedId
	private OTPId otp;
	@Column(name="generated_time")
	private long generatedTime;
	/**
	 * @return the order
	 */
	public Order getOrder() {
		return order;
	}
	/**
	 * @param order the order to set
	 */
	public void setOrder(Order order) {
		this.order = order;
	}
	
	/**
	 * @return the otp
	 */
	public OTPId getOtp() {
		return otp;
	}
	/**
	 * @param otp the otp to set
	 */
	public void setOtp(OTPId otp) {
		this.otp = otp;
	}
	/**
	 * @return the generatedTime
	 */
	public long getGeneratedTime() {
		return generatedTime;
	}
	/**
	 * @param generatedTime the generatedTime to set
	 */
	public void setGeneratedTime(long generatedTime) {
		this.generatedTime = generatedTime;
	}
}
