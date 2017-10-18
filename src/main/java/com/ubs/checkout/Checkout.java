package com.ubs.checkout;

import static org.assertj.core.util.Preconditions.checkNotNull;

import java.math.BigDecimal;

public class Checkout {

	private final PromotionsRepository repository;
	private final Basket basket = new Basket();

	public Checkout(PromotionsRepository repository) {
		checkNotNull(repository, "Promotions repository cannot be null");

		this.repository = repository;
	}

	public void scanItem(Item item) {
		checkNotNull(item, "Item cannot be null");

		basket.addItem(item);
	}

	public BigDecimal totalPrice() {
		BigDecimal basePrice = calculateBasePrice();
		BigDecimal discounts = calculateDiscounts();
		return basePrice.subtract(discounts);
	}

	private BigDecimal calculateBasePrice() {
		return basket.getItems()
			.stream()
			.map(Item::getPrice)
			.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private BigDecimal calculateDiscounts() {
		return repository.getAvailablePromotions()
			.stream()
			.filter(p -> p.isApplicable(basket.getItems()))
			.map(p -> p.calculateDiscount(basket.getItems()))
			.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

}
