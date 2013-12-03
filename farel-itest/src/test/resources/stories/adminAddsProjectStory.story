Adding project

Narrative:
In order to add project
As an admin
I want to have button for it

Scenario: I am on admin's panel page

Given that I access admin panel
When I set <name> as a name for the project
And I click the Add button
Then project will be added to the system

Examples:
|name|
|validName1|
|validName2|
|validName3|
