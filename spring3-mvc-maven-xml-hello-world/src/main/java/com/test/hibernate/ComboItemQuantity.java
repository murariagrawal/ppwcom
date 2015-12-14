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
@Table(name="combo_item_quantity")
public class ComboItemQuantity {
	@Id
	@Column(name = "party_item_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long partyItemId;
	@Column
	private int quantity;
	@ManyToOne
	@JoinColumn(name="item_id")
	private Item comboItem;
	@Column
	private String itemIds;
	
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
	 * @return the comboItem
	 */
	public Item getComboItem() {
		return comboItem;
	}
	/**
	 * @param comboItem the comboItem to set
	 */
	public void setComboItem(Item comboItem) {
		this.comboItem = comboItem;
	}
	/**
	 * @return the itemIds
	 */
	public String getItemIds() {
		return itemIds;
	}
	/**
	 * @param itemIds the itemIds to set
	 */
	public void setItemIds(String itemIds) {
		this.itemIds = itemIds;
	}
	
}
