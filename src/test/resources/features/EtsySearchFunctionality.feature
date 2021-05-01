@HR-5
Feature:Validating Search functionality

  @smoke @regression
  Scenario: Validating search result is matching with search item
    Given User navigates to Etsy application
    When User searches for "carpet"
    Then User validates search results contain
      | carpet   | rug         |
      | oval rug | turkish rug |