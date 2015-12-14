package com.test.hibernate;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "party_item_quantity")
public class PartyItemQuantity {
	@Id
	@Column(name = "party_item_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long partyItemId;
	@Column
	private int quantity;
	@Column
	private BigDecimal price;
	@ManyToOne
	@JoinColumn(name="item_id")
	private Item item;
	/**
	 * @return the partyItemId
	 */
	public long getPartyItemId() {
		return partyItemId;
	}
	/**
	 * @param partyItemId the partyItemId to set
	 */
	public void setPartyItemId(long partyItemId) {
		this.partyItemId = partyItemId;
	}
	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	/**
	 * @return the item
	 */
	public Item getItem() {
		return item;
	}
	/**
	 * @param item the item to set
	 */
	public void setItem(Item item) {
		this.item = item;
	}
	

}
