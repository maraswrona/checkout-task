# checkout-task

This is a really __simple__ checkout component that allows you to scan your products and calculate their final price.

###Features:
- scan items one by one and add to basket
- calculate the total price including any promotions if applicable
- you can provide your Promotion definitions
- Multibuy type Promotion (buy X pay Z) provided out of the box, but you can implement your own

###API Limitations:
- you can only scan one item at a time
- no way to remove item from basket, or empty the basket

###Usage example:

```
// initial setup of data
Item A = new Item("A", new BigDecimal("10"));
Item B = new Item("B", new BigDecimal("20"));
Item C = new Item("C", new BigDecimal("30"));

Promotion PA = new MultibuyPromotion(A, 2, new BigDecimal("15"));
PromotionsRepository repository = new PromotionsRepository(Arrays.asList(PA));

// ... create Checkout component
Checkout checkout = new Checkout(repository);

// ... scan your items
checkout.scanItem(A);
checkout.scanItem(A);
checkout.scanItem(B);
checkout.scanItem(C);
checkout.scanItem(C);

// get the final price
BigDecimal totalPrice = checkout.totalPrice();

System.out.println("Total price is: " + totalPrice);
```
