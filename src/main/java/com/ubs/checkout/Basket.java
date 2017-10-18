package com.ubs.checkout;

import static org.assertj.core.util.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Basket {

	private final List<Item> items = new ArrayList<>();

	public void addItem(Item item) {
		checkNotNull(item, "Item cannot be null");
		items.add(item);
	}

	public List<Item> getItems() {
		return Collections.unmodifiableList(items);
	}

}
