package com.ubs.checkout;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.math.BigDecimal;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableList;

public class CheckoutTest {

	Item A = new Item("A", new BigDecimal("40"));
	Item B = new Item("B", new BigDecimal("10"));
	Item C = new Item("C", new BigDecimal("30"));
	Item D = new Item("D", new BigDecimal("25"));

	MultibuyPromotion PA = new MultibuyPromotion(A, 3, new BigDecimal("70"));
	MultibuyPromotion PB = new MultibuyPromotion(B, 2, new BigDecimal("15"));
	MultibuyPromotion PC = new MultibuyPromotion(C, 1, new BigDecimal("0"));
	MultibuyPromotion PD = new MultibuyPromotion(D, 2, new BigDecimal("0"));
	PromotionsRepository repository = new PromotionsRepository(ImmutableList.of(PA, PB, PC, PD));

	@DataProvider(name = "testCases")
	public Object[][] testCases() {
		return new Object[][]
			{
					// for given items to scan, what is the expected total price?

					// some basic cases
					{ ImmutableList.of(), new BigDecimal("0") },
					{ ImmutableList.of(A), new BigDecimal("40") },
					{ ImmutableList.of(B), new BigDecimal("10") },
					{ ImmutableList.of(C), new BigDecimal("0") },
					{ ImmutableList.of(D), new BigDecimal("25") },

					// various combinations
					{ ImmutableList.of(A, B, C, D), new BigDecimal("75") },
					{ ImmutableList.of(A, A, A), new BigDecimal("70") },
					{ ImmutableList.of(A, A, A, A), new BigDecimal("110") },
					{ ImmutableList.of(A, A, A, B, B), new BigDecimal("85") },

					// free items promotions
					{ ImmutableList.of(A, A, A, C), new BigDecimal("70") },
					{ ImmutableList.of(A, A, A, C, C), new BigDecimal("70") },
					{ ImmutableList.of(C, C, C, C, C), new BigDecimal("0") },
					{ ImmutableList.of(A, A, A, D), new BigDecimal("95") },
					{ ImmutableList.of(A, A, A, D, D), new BigDecimal("70") },

			};
	}

	@Test(dataProvider = "testCases")
	public void verifyCheckoutCalculatesTotalPriceCorrectly(ImmutableList<Item> itemsToScan, BigDecimal expectedPrice) {
		// given

		Checkout checkout = new Checkout(repository);

		// when
		for (Item item : itemsToScan) {
			checkout.scanItem(item);
		}

		// then
		BigDecimal totalPrice = checkout.totalPrice();
		assertThat(totalPrice).isEqualByComparingTo(expectedPrice);

	}

	@Test
	public void verifyConstructorWithNullParameter() {

		// when
		assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> {
			new Checkout(null);
		});

	}

	@Test
	public void verifyScanNullItem() {
		Checkout checkout = new Checkout(repository);

		// when
		assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> {
			checkout.scanItem(null);
		});

	}

}
