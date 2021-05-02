@HR-5
Feature:Validating Search functionality

  Background: Repeated steps
    Given User navigates to Etsy application
    When User searches for "carpet"

  @smoke @regression
  Scenario: Validating search result is matching with search item
    Then User validates search results contain
      | carpet   | rug         |
      | oval rug | turkish rug |


    @regression
    Scenario: Validating price range functionality for search item
      And User selects price range more than 1000
      Then User validates price range is more than 1000