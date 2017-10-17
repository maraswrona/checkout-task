package com.ubs.checkout;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Checkout {

	private final List<Item> items = new ArrayList<>();
	private final List<Promotion> promotions;
	private final Map<String, Item> itemsRepository;

	public Checkout(List<Promotion> promotions, Map<String, Item> itemsRepository) {
		this.promotions = promotions;
		this.itemsRepository = itemsRepository;
	}

	public void scanItem(Item item) {
		items.add(item);
	}

	public BigDecimal totalPrice() {
		BigDecimal basePrice = calculateBasePrice();
		BigDecimal finalPrice = applyPromotions(basePrice);
		return finalPrice;
	}

	private BigDecimal calculateBasePrice() {
		BigDecimal sum = items.stream()
			.map(Item::getPrice)
			.reduce(BigDecimal.ZERO, BigDecimal::add);
		return sum;
	}

	private BigDecimal applyPromotions(BigDecimal basePrice) {

		BigDecimal finalPrice = basePrice;

		for (Promotion promotion : promotions) {

			// 1. check if promotion is applicable to this basket
			// find if basket contains corresponding product
			// count that product
			// if count more than promotion requires
			// then deduct the promotion count * regular price
			// and add promotion price

			int count = (int) items.stream()
				.map(Item::getId)
				.filter(itemId -> itemId.equals(promotion.getItemId()))
				.count();

			if (count >= promotion.getUnits()) {
				// promotion triggered

				int sets = count / promotion.getUnits();
				// int individualItems = count % promotion.getUnits();

				// deduct prices of sets: - sets * promo items * price
				BigDecimal singleItemPrice = itemsRepository.get(promotion.getItemId())
					.getPrice();
				int deductedItems = sets * promotion.getUnits();
				BigDecimal deductedPrice = singleItemPrice.multiply(BigDecimal.valueOf(deductedItems));

				finalPrice = finalPrice.subtract(deductedPrice);

				// add the promotion price
				finalPrice = finalPrice.add(promotion.getDiscountedPrice());
			}

		}

		return finalPrice;
	}

}
