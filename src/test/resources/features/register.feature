Feature: Register

  @register @ios
  Scenario Outline: User successfully registers with valid information
    Given User launches the application
    When User navigates to the register screen
    And User enters first name "<firstName>"
    And User enters last name "<lastName>"
    And User enters registration email "<email>"
    And User enters registration password "<password>"
    And User clicks register button
    Then User account should be created successfully

    Examples:
      | firstName | lastName | email                  | password |
      | Yusuf     | Test     | yusuftest102@gmail.com | 123qwe   |
