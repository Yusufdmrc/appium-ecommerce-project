Feature: Login

  @login @ios
  Scenario Outline: User successfully logs in with valid credentials
    Given User launches the application
    When User enters email "<email>"
    And User enters password "<password>"
    And User clicks login button
    Then User should be logged in successfully

    Examples:
      | email                   | password |
      | yusuftest100@gmail.com  | 123qwe   |

