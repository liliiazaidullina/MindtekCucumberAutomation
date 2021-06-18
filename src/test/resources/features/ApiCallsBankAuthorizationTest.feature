@api @smoke @regression @HR-30
  Feature: Validating Authorization api calls
    Scenario: Validating success response in api call when sending with token
      Given User sends get customers api call with access token
      Then User validates 200 status code

    Scenario: Validating forbidden response in api call when sending without token
      Given User sends get customers api call without access token
      Then User validates 403 status code



