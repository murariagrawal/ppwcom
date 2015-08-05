package com.test.hibernate;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
@Entity
@Table(name="order")
public class Order {
	/**
	 * Order id which is an auto generated field
	 */
	@Id
	@Column(name="order_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long orderId;
	/**
	 * Customer information related to the order. 
	 * This is a one to one mapping with the customer object on the 
	 * basis of the customer id column in the order table.
	 */
	@OneToOne(fetch=FetchType.LAZY, targetEntity=Customer.class)
	private Customer customer;
	@Transient
	private DiscountInformation discountInfo;
	/**
	 * Payment mode of the order which can be a cash or online payment.
	 */
	@Column(name="payment_mode")
	private PaymentMode paymentMode;
	/**
	 * In an order the total box quantity would be the sum of all the
	 * individual item quantity. This is a calculated field. 
	 */
	@Column(name="total_box_quantity")
	private int totalBoxQuantity;
	/**
	 * In an order the total price is the sum of all the individual item price*quantity of the individual items.
	 * This is a calculated field.
	 */
	@Column(name="total_price")
	private BigDecimal totalPrice;
	/**
	 * This is a One to One mapping between the address table on the address id column in the order table.
	 *
	 */
	@OneToOne(targetEntity=Address.class)
	private Address deliveryAddress;
	@OneToOne(targetEntity=Address.class)
	private Crew deliveryCrew;
	@Column
	private Status status;
	@Column(name="ordered_time")
	private Timestamp orderedTime;
	@OneToOne(targetEntity=DeliverySlot.class)
	private DeliverySlot deliverySlotSelected;
	@OneToMany( targetEntity=OrderItems.class)
	private List<OrderItems> orderItems;
	@OneToMany(targetEntity=OrderToppings.class)
	private List<OrderToppings> orderToppings;
	/**
	 * @return the orderId
	 */
	public long getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	/**
	 * @return the customer
	 */
	public Customer getCustomer() {
		return customer;
	}
	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	/**
	 * @return the discountInfo
	 */
	public DiscountInformation getDiscountInfo() {
		return discountInfo;
	}
	/**
	 * @param discountInfo the discountInfo to set
	 */
	public void setDiscountInfo(DiscountInformation discountInfo) {
		this.discountInfo = discountInfo;
	}
	/**
	 * @return the paymentMode
	 */
	public PaymentMode getPaymentMode() {
		return paymentMode;
	}
	/**
	 * @param paymentMode the paymentMode to set
	 */
	public void setPaymentMode(PaymentMode paymentMode) {
		this.paymentMode = paymentMode;
	}
	/**
	 * @return the totalBoxQuantity
	 */
	public int getTotalBoxQuantity() {
		return totalBoxQuantity;
	}
	/**
	 * @param totalBoxQuantity the totalBoxQuantity to set
	 */
	public void setTotalBoxQuantity(int totalBoxQuantity) {
		this.totalBoxQuantity = totalBoxQuantity;
	}
	/**
	 * @return the totalPrice
	 */
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	/**
	 * @param totalPrice the totalPrice to set
	 */
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	/**
	 * @return the deliveryAddress
	 */
	public Address getDeliveryAddress() {
		return deliveryAddress;
	}
	/**
	 * @param deliveryAddress the deliveryAddress to set
	 */
	public void setDeliveryAddress(Address deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	/**
	 * @return the deliveryCrew
	 */
	public Crew getDeliveryCrew() {
		return deliveryCrew;
	}
	/**
	 * @param deliveryCrew the deliveryCrew to set
	 */
	public void setDeliveryCrew(Crew deliveryCrew) {
		this.deliveryCrew = deliveryCrew;
	}
	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}
	/**
	 * @return the orderedTime
	 */
	public Timestamp getOrderedTime() {
		return orderedTime;
	}
	/**
	 * @param orderedTime the orderedTime to set
	 */
	public void setOrderedTime(Timestamp orderedTime) {
		this.orderedTime = orderedTime;
	}
	/**
	 * @return the deliverySlotSelected
	 */
	public DeliverySlot getDeliverySlotSelected() {
		return deliverySlotSelected;
	}
	/**
	 * @param deliverySlotSelected the deliverySlotSelected to set
	 */
	public void setDeliverySlotSelected(DeliverySlot deliverySlotSelected) {
		this.deliverySlotSelected = deliverySlotSelected;
	}
	/**
	 * @return the orderItems
	 */
	public List<OrderItems> getOrderItems() {
		return orderItems;
	}
	/**
	 * @param orderItems the orderItems to set
	 */
	public void setOrderItems(List<OrderItems> orderItems) {
		this.orderItems = orderItems;
	}
	/**
	 * @return the orderToppings
	 */
	public List<OrderToppings> getOrderToppings() {
		return orderToppings;
	}
	/**
	 * @param orderToppings the orderToppings to set
	 */
	public void setOrderToppings(List<OrderToppings> orderToppings) {
		this.orderToppings = orderToppings;
	}
	
}
