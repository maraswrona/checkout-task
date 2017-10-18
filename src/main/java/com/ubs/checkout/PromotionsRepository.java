package com.ubs.checkout;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import com.google.common.collect.ImmutableList;

public class PromotionsRepository {

	private final List<Promotion> availablePromotions;

	public PromotionsRepository(List<Promotion> availablePromotions) {
		checkNotNull(availablePromotions);

		this.availablePromotions = ImmutableList.copyOf(availablePromotions);
	}

	public List<Promotion> getAvailablePromotions() {
		return availablePromotions;
	}
}
