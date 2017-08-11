package com.itv.checkoutkata;

import java.util.ArrayList;

import com.itv.checkoutkata.model.Price;
import com.itv.checkoutkata.model.PriceMatrix;
import com.itv.checkoutkata.model.Promotion;

/**
 * 
 * Helper class which provides Price Matrix test data
 *
 */
public final class PriceMatrixTestData {
	
	private static PriceMatrix priceMatrix = null;

	public static void addProduct(final String sku, final Price price, final Promotion promotion){
		price.setSku(sku);
		price.setPromotion(promotion);
		priceMatrix.getProductPrices().add(price);
	}
	
	public static PriceMatrix getPriceMatrix(){
		return priceMatrix;
	}
	
	public static void initialisePriceMatrix(){
		priceMatrix = new PriceMatrix();
		priceMatrix.setProductPrices(new ArrayList<>());
	}
	
}
