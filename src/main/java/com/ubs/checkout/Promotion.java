package com.ubs.checkout;

import java.math.BigDecimal;
import java.util.List;

public interface Promotion {

	boolean isApplicable(List<Item> itemsInBasket);

	BigDecimal calculateDiscount(List<Item> itemsInBasket);

}
