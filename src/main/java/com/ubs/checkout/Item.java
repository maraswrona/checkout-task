package com.ubs.checkout;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.math.BigDecimal;

public class Item {

	private final String id;
	private final BigDecimal price;

	public Item(String id, BigDecimal price) {
		checkNotNull(id, "Item ID cannot be null");
		checkNotNull(price, "Item price cannot be null");
		checkArgument(price.compareTo(BigDecimal.ZERO) > 0, "Item price must be positive");

		this.id = id;
		this.price = price;
	}

	public String getId() {
		return id;
	}

	public BigDecimal getPrice() {
		return price;
	}

	@Override
	public String toString() {
		return "Item " + id + ": " + price;
	}

}
