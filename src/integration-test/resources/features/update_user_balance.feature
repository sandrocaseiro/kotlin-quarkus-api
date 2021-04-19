#Feature: Service should provide endpoint to update users balances
#  Check if service can update users balances
#
#  Scenario: Unauthenticated user cannot update an user balance
#    When I use the payload:
#    """
#    {
#      "balance": 50.25
#    }
#    """
#    And I PATCH to '/v1/users/1/balance'
#    Then I should receive the status code 403
#
#  Scenario: Cannot update user balance with empty values
#    Given I am authenticated
#    When I use the payload:
#    """
#    {
#      "balance": ""
#    }
#    """
#    And I PATCH to '/v1/users/1/balance'
#    Then I should receive the status code 422
#    And The response has an error with code 905 containing 'balance'
#
#  Scenario: Cannot update user balance with null values
#    Given I am authenticated
#    When I use the payload:
#    """
#    {
#      "balance": null
#    }
#    """
#    And I PATCH to '/v1/users/1/balance'
#    Then I should receive the status code 422
#    And The response has an error with code 905 containing 'balance'
#
#  Scenario: Cannot update user balance with negative values
#    Given I am authenticated
#    When I use the payload:
#    """
#    {
#      "balance": -10.64
#    }
#    """
#    And I PATCH to '/v1/users/1/balance'
#    Then I should receive the status code 422
#    And The response has an error with code 905 containing 'balance'
#
#  Scenario: Cannot update user balance with authenticated user not from Group 1
#    Given I am authenticated with 'user2@mail.com'
#    When I use the payload:
#    """
#    {
#      "balance": 50.25
#    }
#    """
#    And I PATCH to '/v1/users/1/balance'
#    Then I should receive the status code 403
#
#  Scenario: Authenticated user from Group 1 can update a user balance
#    Given I am authenticated
#    When I use the payload:
#    """
#    {
#      "balance": 50.25
#    }
#    """
#    And I PATCH to '/v1/users/1/balance'
#    Then I should receive the status code 204
#    And The user 1 should have his balance updated in the database
