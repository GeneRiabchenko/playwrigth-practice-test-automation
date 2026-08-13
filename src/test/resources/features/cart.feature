Feature: Shopping Cart

  As a customer,
  I want to manage products in my shopping cart
  So that I can review and adjust my order before checkout

  Rule: Customer should be able to add products to the cart
    Example: Actor adds a single product to the cart
      Given Actor is on the home page
      When Actor adds "Combination Pliers" to the cart
      Then the cart badge should show "1"

    Example: Actor adds multiple different products to the cart
      Given Actor is on the home page
      When Actor adds "Combination Pliers" to the cart
      And Actor adds "Pliers" to the cart
      Then the cart badge should show "2"
      And Actor is on the cart page
      And the cart should contain the following products:
        | Product            | Price  |
        | Combination Pliers | $14.15 |
        | Pliers             | $12.01 |
      And the cart total should be "$26.16"

  Rule: Customer should be able to update product quantity in the cart
    Example: Actor increases the quantity of a product in the cart
      Given Actor is on the home page
      When Actor adds "Combination Pliers" to the cart
      And Actor is on the cart page
      And Actor changes the quantity of "Combination Pliers" to "3"
      Then the line total for "Combination Pliers" should be "$42.45"
      And the cart total should be "$42.45"

  Rule: Customer should be able to remove products from the cart
    Example: Actor removes the only product from the cart
      Given Actor is on the home page
      When Actor adds "Combination Pliers" to the cart
      And Actor is on the cart page
      And Actor removes "Combination Pliers" from the cart
      Then the message "The cart is empty. Nothing to display." should be displayed in the cart

    Example: Actor removes one product and keeps the others
      Given Actor is on the home page
      When Actor adds "Combination Pliers" to the cart
      And the cart badge should show "1"
      And Actor adds "Pliers" to the cart
      And the cart badge should show "2"
      And Actor is on the cart page
      And Actor removes "Combination Pliers" from the cart
      Then the cart should contain the following products:
        | Product | Price  |
        | Pliers  | $12.01 |

  Rule: Customer should be able to navigate away from the cart
    Example: Actor continues shopping from the cart
      Given Actor is on the home page
      When Actor adds "Combination Pliers" to the cart
      And Actor is on the cart page
      And Actor clicks continue shopping
      Then Actor should be on the home page

    Example: Actor proceeds to checkout as a guest
      Given Actor is on the home page
      When Actor adds "Combination Pliers" to the cart
      And Actor is on the cart page
      And Actor clicks proceed to checkout
      Then the sign in form should be displayed
