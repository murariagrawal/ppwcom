package com.test.hibernate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name="Item")
public class Item {
	@Id
	@Column(name="item_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long itemId;
	@Column(name="item_name")
	private String itemName;
	@Column(name="item_details")
	private String itemDetails;
	@Column(name="item_price")
	private BigDecimal itemPrice;
	@Column
	private boolean partyItem;
	@Column
	private boolean comboItem;
	@OneToMany(targetEntity=PartyItemQuantity.class, mappedBy="item", cascade=CascadeType.ALL)
	private List<PartyItemQuantity> partyQuantitylist;
	@OneToMany(targetEntity=ComboItemQuantity.class, mappedBy="comboItem", cascade=CascadeType.ALL)
	private List<ComboItemQuantity> comboQuantityList = new ArrayList<ComboItemQuantity>();
	
	/**
	 * @return the itemId
	 */
	public long getItemId() {
		return itemId;
	}
	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}
	/**
	 * @param itemName the itemName to set
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	/**
	 * @return the itemDetails
	 */
	public String getItemDetails() {
		return itemDetails;
	}
	/**
	 * @param itemDetails the itemDetails to set
	 */
	public void setItemDetails(String itemDetails) {
		this.itemDetails = itemDetails;
	}
	/**
	 * @return the itemPrice
	 */
	public BigDecimal getItemPrice() {
		return itemPrice;
	}
	/**
	 * @param itemPrice the itemPrice to set
	 */
	public void setItemPrice(BigDecimal itemPrice) {
		this.itemPrice = itemPrice;
	}
	/**
	 * @return the partyItem
	 */
	public boolean isPartyItem() {
		return partyItem;
	}
	/**
	 * @param partyItem the partyItem to set
	 */
	public void setPartyItem(boolean partyItem) {
		this.partyItem = partyItem;
	}
	/**
	 * @return the partyQuantitylist
	 */
	public List<PartyItemQuantity> getPartyQuantitylist() {
		return partyQuantitylist;
	}
	/**
	 * @param partyQuantitylist the partyQuantitylist to set
	 */
	public void setPartyQuantitylist(List<PartyItemQuantity> partyQuantitylist) {
		this.partyQuantitylist = partyQuantitylist;
	}
	/**
	 * @return the comboItem
	 */
	public boolean isComboItem() {
		return comboItem;
	}
	/**
	 * @param comboItem the comboItem to set
	 */
	public void setComboItem(boolean comboItem) {
		this.comboItem = comboItem;
	}
	/**
	 * @return the comboQuantityList
	 */
	public List<ComboItemQuantity> getComboQuantityList() {
		return comboQuantityList;
	}
	/**
	 * @param comboQuantityList the comboQuantityList to set
	 */
	public void setComboQuantityList(List<ComboItemQuantity> comboQuantityList) {
		this.comboQuantityList = comboQuantityList;
	}
	

}
