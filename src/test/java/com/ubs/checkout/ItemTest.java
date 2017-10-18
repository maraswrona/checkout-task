package com.ubs.checkout;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.math.BigDecimal;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ItemTest {

	@DataProvider(name = "constructorValidationTestCases")
	public Object[][] constructorValidationTestCases() {
		return new Object[][]
			{
					// for given parameters, what is the expected validation result?
					{ "A", new BigDecimal("-1"), IllegalArgumentException.class },
					{ "A", new BigDecimal("0"), IllegalArgumentException.class },
					{ null, new BigDecimal("1"), NullPointerException.class },
					{ "A", null, NullPointerException.class },

			};
	}

	@Test(dataProvider = "constructorValidationTestCases")
	public void verifyConstructorValidation(String id, BigDecimal price, Class<Throwable> expectedException) {

		assertThatExceptionOfType(expectedException).isThrownBy(() -> {
			new Item(id, price);
		});

	}

	@Test
	public void verifyConstructor() {
		// when
		Item item = new Item("A", new BigDecimal("10.23"));

		// then
		assertThat(item.getId()).isEqualTo("A");
		assertThat(item.getPrice()).isEqualByComparingTo(new BigDecimal("10.23"));
	}
}
