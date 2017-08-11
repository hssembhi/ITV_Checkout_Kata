package com.itv.checkoutkata.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.itv.checkoutkata.model.Price;
import com.itv.checkoutkata.model.PriceMatrix;
import com.itv.checkoutkata.validator.CheckoutValidator;

/**
 * 
 * Class responsible for calculating the total price of the products to be checkout
 *
 */
public class CheckoutService {

	private PriceCalculatorService priceCalculatorService = new PriceCalculatorService();
	
	private CheckoutValidator checkoutValidator = new CheckoutValidator();

	/**
	 * Method takes a list of skus and a Price Matrix and returns a calculated total cost in pence
	 *  
	 * @param productSkus - List of skus to be checked out
	 * @param priceMatrix - pricing matrix containing Prices and promotions for each sku
	 * @return - total price in pence
	 */
	public int checkout(final List<String> productSkus, final PriceMatrix priceMatrix) {

		checkoutValidator.validCheckoutRequest(productSkus, priceMatrix);

		final Map<String, Integer> productsAndCost = getProductsAndCost(productSkus, priceMatrix);

		return priceCalculatorService.calculateCheckoutTotal(productsAndCost);
	}
	
	/**
	 * Method takes a list of skus and returning a map of skus and total occurrences
	 * 
	 * @param productSkus - list of skus
	 * @return - map containing a key/value pair of sku/total occurrences
	 */
	private Map<String, Long> getProductsAndQuantity(final List<String> productSkus){
		return productSkus.stream()
				.collect(Collectors.groupingBy(sku -> sku, Collectors.counting()));
	}
	
	/**
	 * Method takes a list of skus and the pricing matrix and returns a map of skus and calculated
	 * total cost for each product.
	 *  
	 * @param productSkus - list of skus to be calculated
	 * @param priceMatrix - pricing matrix containing Prices and promotions each sku 
	 * @return - map containing a key/value pair of sku/total cost
	 */
	private Map<String, Integer> getProductsAndCost(final List<String> productSkus, final PriceMatrix priceMatrix){
		
		final Map<String, Long> productsAndQuantity = getProductsAndQuantity(productSkus);
		final Map<String, Integer> productsAndCost = new HashMap<>();
		
		productsAndQuantity.forEach((sku, qty) -> {
			final Price price = priceCalculatorService.lookupPrice(sku, priceMatrix);
			productsAndCost.put(sku, priceCalculatorService.calculateCostPerUnit(qty, price));
		});
		
		return productsAndCost;
	}

}
