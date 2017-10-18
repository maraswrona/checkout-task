package com.ubs.checkout;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.math.BigDecimal;

import org.testng.annotations.Test;

import com.google.common.collect.ImmutableList;

public class PromotionsRepositoryTest {

	@Test
	public void verifyConstructor() {
		// given
		MultibuyPromotion PA = new MultibuyPromotion(new Item("A", new BigDecimal("40")), 3, new BigDecimal("70"));
		MultibuyPromotion PB = new MultibuyPromotion(new Item("B", new BigDecimal("20")), 2, new BigDecimal("15"));

		// when
		PromotionsRepository repository = new PromotionsRepository(ImmutableList.of(PA, PB));

		// then
		assertThat(repository.getAvailablePromotions()).isNotNull()
			.isNotEmpty()
			.hasSize(2)
			.containsExactly(PA, PB);
	}

	@Test
	public void verifyConstructorWithEmptyParameter() {

		// when
		PromotionsRepository repository = new PromotionsRepository(ImmutableList.of());

		// then
		assertThat(repository.getAvailablePromotions()).isNotNull()
			.isEmpty();
	}

	@Test
	public void verifyConstructorWithNullParameter() {

		// when
		assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> {
			new PromotionsRepository(null);
		});

	}
}
