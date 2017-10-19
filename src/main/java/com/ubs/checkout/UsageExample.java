package com.ubs.checkout;

import java.math.BigDecimal;
import java.util.Arrays;

public class UsageExample {

	public static void main(String[] args) {

		// initial setup of data
		Item A = new Item("A", new BigDecimal("10"));
		Item B = new Item("B", new BigDecimal("20"));
		Item C = new Item("C", new BigDecimal("30"));

		Promotion PA = new MultibuyPromotion(A, 2, new BigDecimal("15"));
		PromotionsRepository repository = new PromotionsRepository(Arrays.asList(PA));

		// ... create Checkout component
		Checkout checkout = new Checkout(repository);

		// ... scan your items
		checkout.scanItem(A);
		checkout.scanItem(A);
		checkout.scanItem(B);
		checkout.scanItem(C);
		checkout.scanItem(C);

		// get the final price
		BigDecimal totalPrice = checkout.totalPrice();

		System.out.println("Total price is: " + totalPrice);

	}

}
