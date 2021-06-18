@api @regression @HR-31
Feature: Validating query parameters in get customers api call

  Scenario Outline:Validating order customers get api call
    Given User creates customers with post api call using data
      | Name               | Address           | isActive |
      | FirstName LastName | 54654 Example st. | true     |
      | Random NameE       | 456 Example st.   | true     |
    When User sends get customer api call with "<order>" order
    Then User validates that customer are in "<order>" order

    Examples:
      | order |
      | asc   |
      | desc  |

  @HR-36
  Scenario Outline: Validating limit customers query parameter in get api call
    Given User creates customers with post api call using data
      | Name               | Address           | isActive |
      | FirstName LastName | 54654 Example st. | true     |
      | Random NameE       | 456 Example st.   | true     |
    When User sends get customers api call with "<limit>" limit
    Then User validates that get customers response has "<limit>" customers
    Examples:
      | limit |
      | 3     |
      | 1     |
      | 0     |
