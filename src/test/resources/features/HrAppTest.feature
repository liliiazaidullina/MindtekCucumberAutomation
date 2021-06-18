@HR-20 # Basically, id of the task/story
  Feature: Validate create employee functionality

    @regression@smoke
      Scenario: Validate created employee persisted in database
        Given user navigates to login page
        When user logs in to HRapp
        And create new employee
        Then user validates new employee is created in database


