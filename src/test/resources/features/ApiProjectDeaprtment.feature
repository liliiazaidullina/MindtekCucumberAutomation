
@ApiProject
    Feature: Department API
      Scenario: Create department api calls validation
        Given User sends Create Department API POST call
        Then User validates department is created in UI in department dropdown
        And User validates with GET department api call
        And User validates with GET department api call using id
        When User sends Delete department api call
        Then User validates department is not shown in UI dropdown
        And User validates department  is not  found in response of get department call


