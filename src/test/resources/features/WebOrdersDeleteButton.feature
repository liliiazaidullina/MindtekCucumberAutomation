Feature: Validating delete and edit functionalities

  @regression
  Scenario: Validating delete selected order functionality in View All Orders part
    Given User navigates to application
    When User logs in with username "Tester" and password "test"
    And User selects to delete order 1 and clicks delete selected button
    Then User validates that order is deleted

  @regression
   Scenario: Validating edit order info functionality in View Orders part
    Given User navigates to application
    When User logs in with username "Tester" and password "test"