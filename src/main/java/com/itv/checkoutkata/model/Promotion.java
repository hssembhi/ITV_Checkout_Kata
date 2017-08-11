package com.itv.checkoutkata.model;

/**
 * 
 * Class that represents Promotion for a Product
 *
 */
public class Promotion {

	private int quantity;
	private int price;

	public Promotion(int quantity, int price) {
		this.quantity = quantity;
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public int getPrice() {
		return price;
	}

}
