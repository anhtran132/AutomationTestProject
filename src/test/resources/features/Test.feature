Feature: User Management
  As an admin, I want to creat, edit, edit, search users of the system

  Scenario : Add a new user with Admin role
    Given Admin is on the User Management page
    When Admin clicks the Add button
    And  Admin enters the user details
    And Admin clicks Save button
    Then Admin should see sucessful created message

  Scenario: Add a new user with ESS role
    Given Admin is on the User Management page
    When Admin clicks the Add button
    And  Admin enters the user details
    And Admin clicks Save button
    Then Admin should see sucessful created message

  Scenario: Edit username of an existing user
    Given Admin is on the User Management page
    When Admin selects a user on table and click Edit button
    And Admin edit user of user
    And Admin click Save button
    Then Admin should see sucessful edited message

  Scenario: Edit status of an existing user
    Given Admin is on the User Management page
    When Admin selects a user on table and click Edit button
    And Admin changes the status of user
    And Admin click Save button
    Then Admin should see sucessful edited message

  Scenario: Edit user password
    Given Admin is on the User Management page
    When Admin selects a user on table and click Edit button
    And Admin clicks change password checkbox
    And Admin enter pasword and confirm password
    And Admin click Save button
    Then Admin should see sucessful edited message

  Scenario: Edit roles of a user
    Given Admin is on the User Management page
    When Admin selects a user and assigns a role
    Then the role should be updated for the user

  Scenario: Delete a user
    Given Admin is on the User Management page
    When Admin selects a user and clicks Delete button
    And Admin click Confirm button
    Then Amin should see sucessful deleted message

  Scenario: Search for a user
    Given Admin is on the User Management page
    When Admin enters a username in the search bar
    Then the system should display the corresponding user details


  Scenario: Add a user with invalid data
    Given Admin is on the User Management page
    When Admin tries to add a user with missing details
    Then the system should display an error message

  Scenario: View user details
    Given Admin is on the User Management page
    When Admin clicks on a user's name
    Then the user details should be displayed

  Scenario: Lock a user account
    Given Admin is on the User Management page
    When Admin selects a user and clicks "Lock"
    Then the user account should be locked

  Scenario: Unlock a user account
    Given Admin is on the User Management page
    When Admin selects a locked user and clicks "Unlock"
    Then the user account should be unlocked
