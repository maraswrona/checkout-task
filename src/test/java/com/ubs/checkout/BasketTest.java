package com.ubs.checkout;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.math.BigDecimal;

import org.testng.annotations.Test;

public class BasketTest {

	@Test
	public void verifyAddItem() {
		// given
		Basket basket = new Basket();
		Item A = new Item("A", new BigDecimal("40"));
		Item B = new Item("B", new BigDecimal("20"));

		// when
		basket.addItem(A);
		basket.addItem(B);

		// then
		assertThat(basket.getItems()).isNotNull()
			.isNotEmpty()
			.containsExactly(A, B);
	}

	@Test
	public void verifyAddNullItem() {
		// given
		Basket basket = new Basket();

		// when
		assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> {
			basket.addItem(null);
		});

	}
}
