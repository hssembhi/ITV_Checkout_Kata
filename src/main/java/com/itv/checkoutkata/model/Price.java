package com.itv.checkoutkata.model;

/**
 * 
 * Class that represents Price for a product
 *
 */
public class Price {

	private Promotion promotion;
	private String sku;
	private int pricePerUnit;

	public Price(int pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	public Promotion getPromotion() {
		return promotion;
	}

	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public int getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(int pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}
	
}
