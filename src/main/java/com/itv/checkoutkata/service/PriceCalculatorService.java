package com.itv.checkoutkata.service;

import java.util.Map;

import com.itv.checkoutkata.model.Price;
import com.itv.checkoutkata.model.PriceMatrix;
import com.itv.checkoutkata.model.Promotion;

public class PriceCalculatorService {

	/**
	 * Method takes a sku and return the price object for the sku from the pricing matrix.
	 * 
	 * @param sku - sku to lookup
	 * @param priceMatrix - pricing matrix containing Prices and promotions for each sku
	 * @return - price object for sku
	 */
	public Price lookupPrice(final String sku, final PriceMatrix priceMatrix) {
		return priceMatrix.getProductPrices()
				.stream()
				.filter(price -> price.getSku().equalsIgnoreCase(sku))
				.findFirst()
				.orElse(null);
	}

	/**
	 * Method takes a map containing skus and total costs and returns the total cost of
	 * all skus in pence.
	 *  
	 * @param skuAndTotalCosts - map containing a key/value pair of sku/total cost
	 * @return - total cost in pence
	 */
	public Integer calculateCheckoutTotal(final Map<String, Integer> skuAndTotalCosts) {
		return skuAndTotalCosts.values()
				.stream()
				.mapToInt(Integer::intValue)
				.sum();
	}

	/**
	 * Method calculates the cost per product and returns a total price for that sku.
	 * The method first checks to see if a promotion is available and if so will return
	 * the promotion price otherwise will return the unit qty * unit price.
	 * 
	 * @param qty - total number of products
	 * @param unitPrice - price per sku
	 * @return - total skus price
	 */
	public Integer calculateCostPerUnit(final Long qty, final Price unitPrice) {

		final int unitQty = qty.intValue();
		final int pricePerUnit = unitPrice.getPricePerUnit();
		final Promotion promotion = unitPrice.getPromotion();

		if (isValidPromotion(promotion)){
			return getPromotionCost(promotion, unitQty, pricePerUnit);
		}

		return unitQty * pricePerUnit;
	}
	
	/**
	 * Method check to see if the promotion is valid by first checking to see if there is
	 * a promotion then checks the promotion price and promotion quantity are not zero and
	 * returns true otherwise method returns false.
	 * 
	 * @param promotion - promotion attached to sku price
	 * @return - True/False
	 */
	private boolean isValidPromotion(final Promotion promotion){
		return promotion != null && promotion.getPrice() != 0 && promotion.getQuantity() != 0;
	}
	
	/**
	 * Method check to see if a promotion can be applied and the number of time it can be applied and
	 * returns a total cost depending on the promotion. If not promotion can be applies the method returns
	 * unit qty * unit price.
	 *  
	 * @param promotion - promotion to be evaluated and applied
	 * @param unitQty - total number of products
	 * @param pricePerUnit - sku price per unit
	 * @return - total cost
	 */
	private Integer getPromotionCost(final Promotion promotion, final int unitQty, final int pricePerUnit){
		final int promotionQty = promotion.getQuantity();
		final int promotionApparences = unitQty / promotionQty;
		
		if (promotionApparences != 0) {
			Integer cost = new Integer(0);
			cost = promotionApparences * promotion.getPrice();
			final int exceedingUnits = unitQty % promotionQty;
			if (exceedingUnits != 0) {
				cost += exceedingUnits * pricePerUnit;
			}
			return cost;
		}
		
		return unitQty * pricePerUnit;
	}
}
