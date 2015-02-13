Feature: Location
  As a developer, I want to access a Google Play fused location services
  
  Scenario: Get single location update
    Given I am in the LocationActivity
    When I press the "single location update" button
    Then I should see location update
    And I should see "location updates ended" text
    
    