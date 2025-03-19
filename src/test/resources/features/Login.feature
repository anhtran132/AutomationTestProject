@regression
Feature: Login functionality for OrangeHRM

  @smoke @valid @login
  Scenario: User logs in with valid credentials
    Given I am on the login page
    When I enter the username "Admin" and password "admin123"
    And I click the login button
    Then I should be redirected to the dashboard page
    And I should see the welcome message "Welcome Admin"

  @negative @login
  Scenario: User tries to log in with incorrect password
    Given I am on the login page
    When I enter the username "Admin" and password "wrongpassword"
    And I click the login button
    Then I should see an error message "Invalid credentials"

  @negative @login @ignore
  Scenario: User tries to log in with incorrect username
    Given I am on the login page
    When I enter the username "InvalidUser" and password "admin123"
    And I click the login button
    Then I should see an error message "Invalid credentials"

  @negative @login @ignore
  Scenario: User tries to log in with empty username and password
    Given I am on the login page
    When I leave the username field empty and the password field empty
    And I click the login button
    Then I should see an error message "Username cannot be empty" and "Password cannot be empty"

  @edge @login @ignore
  Scenario: User tries to log in with special characters in username and password
    Given I am on the login page
    When I enter the username "@dm!n" and password "p@ssw0rd$"
    And I click the login button
    Then I should see the error message "Invalid credentials"


  @smoke @ignore
  Scenario Outline: User tries to log in with multiple account
    Given I am on the login page
    When I enter the username "<username>" and password "<password>"
    And I click the login button
    Then I should be redirected to the dashboard page
    And I should see the welcome message "Welcome Admin"
    Examples:
      | username  | password  |
      | username1 | password1 |







