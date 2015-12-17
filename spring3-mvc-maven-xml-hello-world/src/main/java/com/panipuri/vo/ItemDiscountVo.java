package com.panipuri.vo;

public class ItemDiscountVo {
	private ItemVo item;
	private DiscountVo discountForItem;
	public ItemVo getItem() {
		return item;
	}
	public void setItem(ItemVo item) {
		this.item = item;
	}
	public DiscountVo getDiscountForItem() {
		return discountForItem;
	}
	public void setDiscountForItem(DiscountVo discountForItem) {
		this.discountForItem = discountForItem;
	}

}
