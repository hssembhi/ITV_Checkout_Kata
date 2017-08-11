package com.itv.checkoutkata.model;

import java.util.List;

/**
 * 
 * Class acts as the Pricing Matrix for all Products by being loaded
 * with a list of Prices for each product.
 *
 */
public class PriceMatrix {

	private List<Price> productPrices;

	public List<Price> getProductPrices() {
		return productPrices;
	}

	public void setProductPrices(List<Price> productPrices) {
		this.productPrices = productPrices;
	}
}
