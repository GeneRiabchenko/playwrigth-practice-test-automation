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

  Rule: Customer should be able to sort product by criteria
    Scenario Outline: Actor sorts product by Name
      Given Actor is on the home page
      When Actor sorts by "<Sorting>"
      Then the following product name are shown:
        | <product1>  |
        | <product2>  |
        | <product3>  |
        | <product4>  |
        | <product5>  |
        | <product6>  |
        | <product7>  |
        | <product8>  |
        | <product9>  |
      Examples:
      | Sorting      | product1           | product2             | product3     | product4     | product5    | product6          | product7        | product8                           | product9                               |
      | Name (A - Z) | Adjustable Wrench  | Angled Spanner       | Belt Sander  | Bolt Cutters | Chisels Set | Circular Saw      | Claw Hammer     | Claw Hammer with Fiberglass Handle |  Claw Hammer with Shock Reduction Grip |
      | Name (Z - A) | Wood Saw           | Wood Carving Chisels | Washers      | Tool Cabinet | Thor Hammer | Tape Measure 7.5m | Tape Measure 5m | Swiss Woodcarving Chisels | Super-thin Protection Gloves                    |

    Scenario Outline: Actor sorts product by Price
      Given Actor is on the home page
      When Actor sorts by "<Sorting>"
      Then the following product prices are shown:
        | <price1>  |
        | <price2>  |
        | <price3>  |
        | <price4>  |
        | <price5>  |
        | <price6>  |
        | <price7>  |
        | <price8>  |
        | <price9>  |
      Examples:
        | Sorting            | price1 | price2 | price3 | price4 | price5 | price6 | price7 | price8 | price9 |
        | Price (High - Low) | $89.55 | $86.71 | $80.19 | $73.59 | $66.54 | $61.16 | $58.48 | $48.41 | $46.50 |
        | Price (Low - High) | $3.55  | $3.95  | $4.65  | $4.92  | $5.55  | $6.25  | $7.23  | $7.99  | $9.17  |
