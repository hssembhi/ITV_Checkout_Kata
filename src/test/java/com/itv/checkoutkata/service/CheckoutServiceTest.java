package com.itv.checkoutkata.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.itv.checkoutkata.PriceMatrixTestData;
import com.itv.checkoutkata.model.Price;
import com.itv.checkoutkata.model.PriceMatrix;
import com.itv.checkoutkata.model.Promotion;

public class CheckoutServiceTest {

	CheckoutService checkoutService = null;
	List<String> basket = null;
	
	private static final String PRODUCT_SKU_A = "A";
	private static final String PRODUCT_SKU_B = "B";
	private static final String PRODUCT_SKU_C = "C";
	private static final String PRODUCT_SKU_D = "D";
	private static final String PRODUCT_SKU_Y = "Y";
	private static final String PRODUCT_SKU_Z = "Z";
	
	@Before
	public void initalise(){
		PriceMatrixTestData.initialisePriceMatrix();
		checkoutService = new CheckoutService();
		basket = new ArrayList<>();
	}
	
	@Test
	public void testForInitalisedCheckoutService(){
		Assert.assertNotNull(checkoutService);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testForNoProductsToCheckout(){
		checkoutService.checkout(Collections.emptyList(), new PriceMatrix());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testForNoPricingMatrixAtCheckout(){
		basket.add(PRODUCT_SKU_A);
		checkoutService.checkout(basket, null);
	}
	
	@Test(expected=NoSuchElementException.class)
	public void testForProductsWithMissingPriceMatrix(){
		PriceMatrixTestData.addProduct(PRODUCT_SKU_A, new Price(50), null);
		PriceMatrixTestData.addProduct(PRODUCT_SKU_B, new Price(50), null);
		
		basket.add(PRODUCT_SKU_A);
		basket.add(PRODUCT_SKU_B);
		
		// missing from Price Matrix
		basket.add(PRODUCT_SKU_Y);
		basket.add(PRODUCT_SKU_Z); 
		
		checkoutService.checkout(basket, PriceMatrixTestData.getPriceMatrix());
	}
	
	@Test
	public void testForPriceOnSingleProductWithNoPromotions(){
		PriceMatrixTestData.addProduct(PRODUCT_SKU_A, new Price(50), null);
		basket.add(PRODUCT_SKU_A);
		final int totalPrice = checkoutService.checkout(basket, PriceMatrixTestData.getPriceMatrix());
		Assert.assertEquals(50, totalPrice);
	}
	
	@Test
	public void testForPriceOnTwoProductsWithNoPromotions(){
		PriceMatrixTestData.addProduct(PRODUCT_SKU_A, new Price(40), null);
		PriceMatrixTestData.addProduct(PRODUCT_SKU_B, new Price(60), null);
		
		basket.add(PRODUCT_SKU_A);
		basket.add(PRODUCT_SKU_B);
		
		final int totalPrice = checkoutService.checkout(basket, PriceMatrixTestData.getPriceMatrix());
		
		Assert.assertEquals(100, totalPrice);
	}
	
	@Test
	public void testForPriceOnTwoProductsWithAPromotion(){
		PriceMatrixTestData.addProduct(PRODUCT_SKU_A, new Price(50), new Promotion(3, 70));
		
		basket.add(PRODUCT_SKU_A);
		basket.add(PRODUCT_SKU_A);

		final int totalPrice = checkoutService.checkout(basket, PriceMatrixTestData.getPriceMatrix());
		Assert.assertEquals(100, totalPrice);
	}
	
	@Test
	public void testForPriceOnProductWithZeroPromotionPriceAndQuantity(){
		PriceMatrixTestData.addProduct(PRODUCT_SKU_A, new Price(50), new Promotion(0, 0));
		
		basket.add(PRODUCT_SKU_A);

		final int totalPrice = checkoutService.checkout(basket, PriceMatrixTestData.getPriceMatrix());
		Assert.assertEquals(50, totalPrice);
	}
	
	@Test
	public void testForPriceForManyProductsWithAPromotion(){
		PriceMatrixTestData.addProduct(PRODUCT_SKU_B, new Price(50), new Promotion(3, 130));
		PriceMatrixTestData.addProduct(PRODUCT_SKU_C, new Price(80), new Promotion(3, 310));
		PriceMatrixTestData.addProduct(PRODUCT_SKU_D, new Price(120), new Promotion(2, 200));
		PriceMatrixTestData.addProduct(PRODUCT_SKU_A, new Price(40), new Promotion(2, 60));
		
		basket.add(PRODUCT_SKU_A);
		basket.add(PRODUCT_SKU_A);
		basket.add(PRODUCT_SKU_A);
		
		basket.add(PRODUCT_SKU_D);
		basket.add(PRODUCT_SKU_D);

		basket.add(PRODUCT_SKU_B);
		basket.add(PRODUCT_SKU_B);
		basket.add(PRODUCT_SKU_B);
		basket.add(PRODUCT_SKU_B);
		basket.add(PRODUCT_SKU_B);
		
		basket.add(PRODUCT_SKU_C);

		final int totalPrice = checkoutService.checkout(basket, PriceMatrixTestData.getPriceMatrix());
		
		Assert.assertEquals(610, totalPrice);
	}
	
	@Test
	public void testForPriceForProductsWithAndWithoutPromotions(){
		PriceMatrixTestData.addProduct(PRODUCT_SKU_A, new Price(40), null);
		PriceMatrixTestData.addProduct(PRODUCT_SKU_B, new Price(50), new Promotion(2, 100));
		PriceMatrixTestData.addProduct(PRODUCT_SKU_C, new Price(80), new Promotion(2, 200));
		
		basket.add(PRODUCT_SKU_A);
		basket.add(PRODUCT_SKU_A);
		basket.add(PRODUCT_SKU_A);
		
		basket.add(PRODUCT_SKU_B);
		basket.add(PRODUCT_SKU_B);
		
		basket.add(PRODUCT_SKU_C);
		basket.add(PRODUCT_SKU_C);
		basket.add(PRODUCT_SKU_C);

		final int totalPrice = checkoutService.checkout(basket, PriceMatrixTestData.getPriceMatrix());
		
		Assert.assertEquals(500, totalPrice);
	}
	
}
