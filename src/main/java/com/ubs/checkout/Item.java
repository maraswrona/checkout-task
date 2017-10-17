package com.ubs.checkout;

import java.math.BigDecimal;

public class Item {

	private final String id;
	private final BigDecimal price;

	public Item(String id, BigDecimal price) {
		this.id = id;
		this.price = price;
	}

	public String getId() {
		return id;
	}

	public BigDecimal getPrice() {
		return price;
	}

}
