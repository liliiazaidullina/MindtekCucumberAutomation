@api @smoke @regression @HR-60
  Feature: Validating account balance update for API calls
    Scenario: Balance update for an account api calls validation
      Given User creates customers with post api call using data
        | Name       | Address           | isActive |
        | Abcd BBBBB | 54654 Example st. | true     |
      When User creates account for a customer with data
        |accountType|Checking|
        |Balance    |500.0   |
      Then User validates that customer is linked to created account
        |accountType|Checking|
        |Balance    |5000.0   |
      When User updates account balance with put API calls
        |accountType|Checking|
        |Balance    |1000.0   |
      Then User validates that balance is updates
        |accountType|Checking|
        |Balance    |4000.0   |
      When User deletes created account
      Then Use validates that account is deleted
      When User deletes created customer
      Then User validates that customer is deleted

