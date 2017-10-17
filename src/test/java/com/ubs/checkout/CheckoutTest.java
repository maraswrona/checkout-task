package com.ubs.checkout;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.common.base.Functions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class CheckoutTest {

	@Test
	public void testNoPromotions() {
		// given
		Item A = new Item("A", BigDecimal.valueOf(40));
		Item B = new Item("B", BigDecimal.valueOf(10));
		Item C = new Item("C", BigDecimal.valueOf(30));
		Item D = new Item("D", BigDecimal.valueOf(25));

		Map<String, Item> items = ImmutableList.of(A, B, C, D)
			.stream()
			.collect(ImmutableMap.toImmutableMap(Item::getId, Functions.identity()));

		List<Promotion> promotions = ImmutableList.of();
		Checkout checkout = new Checkout(promotions, items);

		// when
		checkout.scanItem(A);
		checkout.scanItem(B);
		checkout.scanItem(C);
		checkout.scanItem(D);

		// then
		BigDecimal totalPrice = checkout.totalPrice();

		assertThat(totalPrice).isEqualByComparingTo(BigDecimal.valueOf(105));
	}

	@Test
	public void testWithPromotions() {
		// given
		Item A = new Item("A", BigDecimal.valueOf(40));
		Item B = new Item("B", BigDecimal.valueOf(10));
		Item C = new Item("C", BigDecimal.valueOf(30));
		Item D = new Item("D", BigDecimal.valueOf(25));

		Map<String, Item> items = ImmutableList.of(A, B, C, D)
			.stream()
			.collect(ImmutableMap.toImmutableMap(Item::getId, Functions.identity()));

		Promotion P1 = new Promotion("A", 3, BigDecimal.valueOf(70));
		Promotion P2 = new Promotion("B", 2, BigDecimal.valueOf(15));
		List<Promotion> promotions = ImmutableList.of(P1, P2);

		Checkout checkout = new Checkout(promotions, items);

		// when
		checkout.scanItem(A);
		checkout.scanItem(B);
		checkout.scanItem(C);
		checkout.scanItem(D);

		// then
		BigDecimal totalPrice = checkout.totalPrice();

		assertThat(totalPrice).isEqualByComparingTo(BigDecimal.valueOf(105));
	}

	@Test
	public void testWithPromotions2() {
		// given
		Item A = new Item("A", BigDecimal.valueOf(40));
		Item B = new Item("B", BigDecimal.valueOf(10));
		Item C = new Item("C", BigDecimal.valueOf(30));
		Item D = new Item("D", BigDecimal.valueOf(25));

		Map<String, Item> items = ImmutableList.of(A, B, C, D)
			.stream()
			.collect(ImmutableMap.toImmutableMap(Item::getId, Functions.identity()));

		Promotion P1 = new Promotion("A", 3, BigDecimal.valueOf(70));
		Promotion P2 = new Promotion("B", 2, BigDecimal.valueOf(15));
		List<Promotion> promotions = ImmutableList.of(P1, P2);

		Checkout checkout = new Checkout(promotions, items);

		// when
		checkout.scanItem(A);
		checkout.scanItem(A);
		checkout.scanItem(A);

		// then
		BigDecimal totalPrice = checkout.totalPrice();

		assertThat(totalPrice).isEqualByComparingTo(BigDecimal.valueOf(70));
	}

	Item A = new Item("A", BigDecimal.valueOf(40));
	Item B = new Item("B", BigDecimal.valueOf(10));
	Item C = new Item("C", BigDecimal.valueOf(30));
	Item D = new Item("D", BigDecimal.valueOf(25));

	Map<String, Item> items = ImmutableList.of(A, B, C, D)
		.stream()
		.collect(ImmutableMap.toImmutableMap(Item::getId, Functions.identity()));

	Promotion P1 = new Promotion("A", 3, BigDecimal.valueOf(70));
	Promotion P2 = new Promotion("B", 2, BigDecimal.valueOf(15));
	List<Promotion> promotions = ImmutableList.of(P1, P2);

	@DataProvider(name = "parameters")
	public Object[][] parameters() {
		return new Object[][] { 
			{ 0, ImmutableList.of() }, 
			{ 105, ImmutableList.of(A, B, C, D) },			
			{ 70, ImmutableList.of(A, A, A) },			
			{ 110, ImmutableList.of(A, A, A, A) },			
			{ 85, ImmutableList.of(A, A, A, B, B) },			
		};
	}

	@Test(dataProvider = "parameters")
	public void test(int expectedPrice, ImmutableList<Item> itemsToScan) {
		// given 
		Checkout checkout = new Checkout(promotions, items);
		
		// when
		for(Item item : itemsToScan) {
			checkout.scanItem(item);
		}
		
		// then
		BigDecimal totalPrice = checkout.totalPrice();
		assertThat(totalPrice).isEqualByComparingTo(BigDecimal.valueOf(expectedPrice));
		
	}

}
