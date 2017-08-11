package com.itv.checkoutkata.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.itv.checkoutkata.model.PriceMatrix;

@Service
public class CheckoutValidator {

	private static final String NO_PRODUCTS_ERROR_MESSAGE = "There are no products to checkout";
	private static final String NO_PRICE_MATRIX_ERROR_MESSAGE = "There are no price matrix for the products";
	private static final String NO_PRICE_MATRIX_FOR_PRODUCT_ERROR_MESSAGE = "No Price Matrix found for Product with SKU ";
	
	public void validCheckoutRequest(final List<String> productSkus, final PriceMatrix priceMatrix) {
		validateProductSkus(productSkus);
		validatePriceMatrix(priceMatrix);
		validateProductSkusAgainstPriceMatrix(productSkus, priceMatrix);
	}
	
	private void validateProductSkus(final List<String> productSkus){
		if (productSkus.isEmpty()) {
			throw new IllegalArgumentException(NO_PRODUCTS_ERROR_MESSAGE);
		}
	}
	
	private void validatePriceMatrix(final PriceMatrix priceMatrix){
		if (priceMatrix == null) {
			throw new IllegalArgumentException(NO_PRICE_MATRIX_ERROR_MESSAGE);
		}
	}

	private void validateProductSkusAgainstPriceMatrix(final List<String> productSkus, final PriceMatrix priceMatrix){
		
		final List<String> priceMatrixSkus = new ArrayList<>();
		
		priceMatrix.getProductPrices().forEach(price -> {
			priceMatrixSkus.add(price.getSku());
		});
		
		final List<String> missingPriceMatrix =  productSkus.stream()
															.filter(sku -> !priceMatrixSkus.contains(sku))
															.collect(Collectors.toList());
		
		if(!missingPriceMatrix.isEmpty()){
			throw new NoSuchElementException(NO_PRICE_MATRIX_FOR_PRODUCT_ERROR_MESSAGE + missingPriceMatrix);
		}
	}
}
