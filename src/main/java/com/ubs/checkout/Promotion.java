package com.ubs.checkout;

import java.math.BigDecimal;

public class Promotion {

	private final String itemId;
	private final int units;
	private final BigDecimal discountedPrice;

	public Promotion(String itemId, int units, BigDecimal discountedPrice) {
		this.itemId = itemId;
		this.units = units;
		this.discountedPrice = discountedPrice;
	}

	public String getItemId() {
		return itemId;
	}

	public int getUnits() {
		return units;
	}

	public BigDecimal getDiscountedPrice() {
		return discountedPrice;
	}

}
