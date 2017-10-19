package com.ubs.checkout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.base.Preconditions;

public class Basket {

	private final List<Item> items = new ArrayList<>();

	public void addItem(Item item) {
		Preconditions.checkNotNull(item, "Item cannot be null");
		items.add(item);
	}

	public List<Item> getItems() {
		return Collections.unmodifiableList(items);
	}

}
