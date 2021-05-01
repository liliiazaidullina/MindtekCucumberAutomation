@HR-6
Feature: Validating order creation functionality
  # it has to be first scenarios.
  Background: These steps will run before each scenario
    Given User navigates to application
    When User logs in with username "Tester" and password "test"
    And User clicks on Order module

    @regression
  Scenario Outline: Validating calculate total functionality
    And User provides product "<Product>" and quantity <Quantity>
    Then User validates total is calculated properly for quantity <Quantity>
    Examples:
      | Product     | Quantity |
      | FamilyAlbum | 1        |
      | MyMoney     | 5        |
      | ScreenSaver | 20       |

      @smoke @regression
  Scenario: Validating order creation functionality
    And User creates and order with data
      | Product     | Quantity | Customer name | Street      | City     | State | Zip   | Card | Card Nr   | Exp Date |
      | FamilyAlbum | 1        | John Doe      | 123 Dee rd. | Chicago  | IL    | 12345 | Visa | 123456789 | 12/21    |
      | MyMoney     | 5        | Patel Harsh   | 123 Dee rd. | New York | NY    | 65432 | Visa | 123498765 | 12/21    |
#     data.get(0).get("Product"); ->FamilyAlbum
    Then User validates success message "New order has been successfully added."
    Then User validates that created orders are in the list of all orders






