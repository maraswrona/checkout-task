package com.ubs.checkout;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.math.BigDecimal;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableList;

public class MultibuyPromotionTest {

	Item A = new Item("A", new BigDecimal("40"));
	Item B = new Item("B", new BigDecimal("10"));
	Item C = new Item("C", new BigDecimal("30"));
	Item D = new Item("D", new BigDecimal("2.5"));

	MultibuyPromotion PA = new MultibuyPromotion(A, 3, new BigDecimal("70"));
	MultibuyPromotion PB = new MultibuyPromotion(B, 2, new BigDecimal("15"));
	MultibuyPromotion PC = new MultibuyPromotion(C, 1, new BigDecimal("1"));
	MultibuyPromotion PD = new MultibuyPromotion(D, 2, new BigDecimal("4.5"));

	MultibuyPromotion notProfitablePromotion = new MultibuyPromotion(A, 2, new BigDecimal("100"));
	MultibuyPromotion freeItemsPromotion = new MultibuyPromotion(A, 2, new BigDecimal("0"));

	@DataProvider(name = "isApplicableTestCases")
	public Object[][] isApplicableTestCases() {
		return new Object[][]
			{
					// for a given basket, is this promotion applicable?
					{ ImmutableList.of(), PA, false },
					{ ImmutableList.of(), PB, false },
					{ ImmutableList.of(), PC, false },

					{ ImmutableList.of(A, B, C, D), PA, false },
					{ ImmutableList.of(A, B, C, D), PB, false },
					{ ImmutableList.of(A, B, C, D), PC, true },

					{ ImmutableList.of(A, A, A), PA, true },
					{ ImmutableList.of(A, A, A), PB, false },
					{ ImmutableList.of(A, A, A), PC, false },

					{ ImmutableList.of(A, A, A, A), PA, true },
					{ ImmutableList.of(A, A, A, A), PB, false },
					{ ImmutableList.of(A, A, A, A), PC, false },

					{ ImmutableList.of(A, A, A, B, B), PA, true },
					{ ImmutableList.of(A, A, A, B, B), PB, true },
					{ ImmutableList.of(A, A, A, B, B), PC, false },

			};
	}

	@Test(dataProvider = "isApplicableTestCases")
	public void verifyThatForAGivenBasketPromotionIsApplicable(List<Item> itemsInBasket, Promotion promotion,
			boolean expectedIsApplicable) {
		// when
		boolean isApplicable = promotion.isApplicable(itemsInBasket);

		// then
		assertThat(isApplicable).isEqualTo(expectedIsApplicable);
	}

	@DataProvider(name = "calculateDiscountTestCases")
	public Object[][] calculateDiscountTestCases() {
		return new Object[][]
			{
					// for a given basket, what is the discount value for that promotion?
					{ ImmutableList.of(A, A, A), PA, new BigDecimal("50") },
					{ ImmutableList.of(A, A, A, A), PA, new BigDecimal("50") },
					{ ImmutableList.of(A, A, A, A, A), PA, new BigDecimal("50") },
					{ ImmutableList.of(A, A, A, A, A, A), PA, new BigDecimal("100") },
					{ ImmutableList.of(A, A, A, A, A, A, A), PA, new BigDecimal("100") },
					{ ImmutableList.of(A, A, A, A, A, A, A, A), PA, new BigDecimal("100") },
					{ ImmutableList.of(A, A, A, A, A, A, A, A, A), PA, new BigDecimal("150") },
					{ ImmutableList.of(A, A, A, A, A, A, A, A, A, A), PA, new BigDecimal("150") },
					{ ImmutableList.of(A, A, A, A, A, A, A, A, A, A, A), PA, new BigDecimal("150") },

					{ ImmutableList.of(C), PC, new BigDecimal("29") },
					{ ImmutableList.of(C, C), PC, new BigDecimal("58") },
					{ ImmutableList.of(C, C, C), PC, new BigDecimal("87") },

					{ ImmutableList.of(D, D), PD, new BigDecimal("0.5") },
					{ ImmutableList.of(D, D, D), PD, new BigDecimal("0.5") },
					{ ImmutableList.of(D, D, D, D), PD, new BigDecimal("1") },
					{ ImmutableList.of(D, D, D, D, D), PD, new BigDecimal("1") },
					{ ImmutableList.of(D, D, D, D, D, D), PD, new BigDecimal("1.5") },
					
					{ ImmutableList.of(A, A), notProfitablePromotion, new BigDecimal("0") },

					{ ImmutableList.of(A, A), freeItemsPromotion, new BigDecimal("80") },

			};
	}

	@Test(dataProvider = "calculateDiscountTestCases")
	public void verifyThatForAGivenBasketPromotionCalculatesDiscount(List<Item> itemsInBasket, Promotion promotion,
			BigDecimal expectedDiscount) {
		// when
		BigDecimal discount = promotion.calculateDiscount(itemsInBasket);

		// then
		assertThat(discount).isEqualByComparingTo(expectedDiscount);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void verifyIsApplicableValidation() {
		PA.isApplicable(null);
	}

	@DataProvider(name = "constructorValidationTestCases")
	public Object[][] constructorValidationTestCases() {
		return new Object[][]
			{
					// for given parameters, what is the expected validation result?
					{ null, 1, new BigDecimal("1.5"), NullPointerException.class },
					{ A, 1, null, NullPointerException.class },
					{ A, 0, new BigDecimal("1.5"), IllegalArgumentException.class },
					{ A, -1, new BigDecimal("1.5"), IllegalArgumentException.class },
					{ A, 1, new BigDecimal("-1"), IllegalArgumentException.class }, };
	}

	@Test(dataProvider = "constructorValidationTestCases")
	public void verifyConstructorValidation(Item item, int itemAmount, BigDecimal discountedPrice,
			Class<Throwable> expectedException) {

		assertThatExceptionOfType(expectedException).isThrownBy(() -> {
			new MultibuyPromotion(item, itemAmount, discountedPrice);
		});

	}

}
