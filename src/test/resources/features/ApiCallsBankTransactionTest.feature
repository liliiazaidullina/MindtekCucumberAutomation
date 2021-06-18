@api @smoke @regression @HR-50
  Feature: Validating Transaction api calls
    Scenario: Validating if transaction amount is applied to balance
      Given User creates customers with post api call using data
        | Name       | Address           | isActive |
        | Abcd BBBBB | 54654 Example st. | true     |
      When User creates account for a customer with data
        |accountType|Checking|
        |Balance    |500.0   |
      And User creates transaction for an account with data
      |transactionName|transactionAmount|
      |Gas transaction| 20              |
      Then User validates that balance is decreased in account
