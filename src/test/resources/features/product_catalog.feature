Feature: Product Catalog

  As a customer,
  I want to easily search, filter, and sort products in the catalog
  So that I can find what I need quickly

  Rule: Customer should be able to search for products by name
    Example: Actor searches for Combination Pliers
      Given Actor is on the home page
      When he searches for "Combination Pliers"
      Then the "Combination Pliers" product should be displayed

    Example: Actor searches for general term
      Given Actor is on the home page
      When he searches for "Pliers"
      Then the following products are shown:
      | Product            | Price  |
      | Combination Pliers | $14.15 |
      | Pliers             | $12.01 |
      | Long Nose Pliers   | $14.24 |
      | Slip Joint Pliers  | $9.17  |

    Example: Actor searches for product that doesn't exist
      Given Actor is on the home page
      When he searches for "Product-does-not-exist"
      Then no products should be displayed
      And the message "There are no products found." should be displayed

  Rule: Customer should be able to filter by category
    Example: Actor filters by Hand Tools category
      Given Actor is on the home page
      When Actor selects following categories:
      | Hand Saw   |
      | Hammer     |
      Then the following products are shown:
        | Product                                | Price  |
        | Claw Hammer with Shock Reduction Grip  | $13.41 |
        | Hammer                                 | $12.58 |
        | Claw Hammer                            | $11.48 |
        | Thor Hammer                            | $11.14 |
        | Sledgehammer                           | $17.75 |
        | Claw Hammer with Fiberglass Handle     | $20.14 |
        | Court Hammer                           | $18.63 |
        | Wood Saw                               | $12.18 |
