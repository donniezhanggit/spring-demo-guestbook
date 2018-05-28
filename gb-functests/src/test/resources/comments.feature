Feature: Comments
    Scenario: Creating a new comment
        Given a comment input
        And anon name 'Vasya'
        And message 'Pupkin'
        When I submit the comment input
        Then response has status CREATED
        And response body has ID of created comment
