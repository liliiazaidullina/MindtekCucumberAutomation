#Validateing Create customer api call works as expected
  #Create a customer
  #Get Customer
  #Delete customer
  #Get Customer and validate the customer is deleted

@api @smoke @regression @HR-44
Feature: Validating customers api call

  Scenario: Validating create customer api call
    Given User creates customers with post api call using data
      | Name               | Address           | isActive |
      | FirstName LastName | 54654 Example st. | true     |
    Then User validates that customer is created with data
      | Name               | Address           | isActive |
      | FirstName LastName | 54654 Example st. | true     |
    When User deletes created customer
    Then User validates that customer is deleted

  Scenario: Validating creating account for a customer
    Given User creates customers with post api call using data
      | Name       | Address           | isActive |
      | Abcd BBBBB | 54654 Example st. | true     |
    When User creates account for a customer with data
    |accountType|Checking|
    |Balance    |500.0   |
    Then User validates that customer is linked to created account
      |accountType|Checking|
      |Balance    |500.0   |
    When User deletes created account
    Then Use validates that account is deleted
    When User deletes created customer
    Then User validates that customer is deleted








