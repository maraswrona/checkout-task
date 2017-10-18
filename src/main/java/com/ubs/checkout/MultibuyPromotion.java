package com.ubs.checkout;

import static com.google.common.base.Preconditions.checkArgument;
import static org.assertj.core.util.Preconditions.checkNotNull;

import java.math.BigDecimal;
import java.util.List;

public class MultibuyPromotion implements Promotion {

	private final Item itemOnPromotion;
	private final int discountedItemsAmount;
	private final BigDecimal discountedPrice;

	public MultibuyPromotion(Item itemOnPromotion, int discountedItemsAmount, BigDecimal discountedPrice) {
		checkNotNull(itemOnPromotion, "Item on promotion cannot be null");
		checkNotNull(discountedPrice, "Special discounted price cannot be null");
		checkArgument(discountedPrice.compareTo(BigDecimal.ZERO) >= 0, "Special discounted price cannot be negative");
		checkArgument(discountedItemsAmount > 0, "Discounted items amount must be positive number");

		this.itemOnPromotion = itemOnPromotion;
		this.discountedItemsAmount = discountedItemsAmount;
		this.discountedPrice = discountedPrice;
	}

	@Override
	public boolean isApplicable(List<Item> itemsInBasket) {
		checkNotNull(itemsInBasket);
		return countApplicableItems(itemsInBasket) >= discountedItemsAmount;
	}

	@Override
	public BigDecimal calculateDiscount(List<Item> itemsInBasket) {
		checkArgument(isApplicable(itemsInBasket), "Cannot apply this promotion on provided basket");

		int groupsOfApplicableItems = countApplicableItems(itemsInBasket) / discountedItemsAmount;
		int applicableItems = groupsOfApplicableItems * discountedItemsAmount;
		BigDecimal regularItemPrice = itemOnPromotion.getPrice();
		BigDecimal totalRegularPrice = regularItemPrice.multiply(BigDecimal.valueOf(applicableItems));
		BigDecimal totalDiscountedPrice = discountedPrice.multiply(BigDecimal.valueOf(groupsOfApplicableItems));
		BigDecimal savings = totalRegularPrice.subtract(totalDiscountedPrice);

		return savings.max(BigDecimal.ZERO);
	}

	private int countApplicableItems(List<Item> items) {
		return (int) items.stream()
			.filter(item -> item.getId()
				.equals(itemOnPromotion.getId()))
			.count();
	}

	@Override
	public String toString() {
		return "Buy " + discountedItemsAmount + " x " + itemOnPromotion.getId() + " pay " + discountedPrice;
	}

}
