Feature: Comments
    Scenario: Creating a new comment
        Given a comment input
        And anon name is 'Vasya'
        And message is 'Pupkin'
        When I submit the comment input
        Then response has status CREATED
        And response body has ID of created comment

    Scenario: Submit a new comment without message
        Given a comment input
        And anon name is 'Anon'
        And no message
        When I submit the comment input
        Then response has status PRECONDITION_FAILED
        And response body contains 'message'
        And response body contains 'must not be null'

    Scenario: Submit a new comment with too short anon name
        Given a comment input
        And anon name is 'A'
        And message is 'Hello, World!'
        When I submit the comment input
        Then response has status PRECONDITION_FAILED
        And response body contains 'anonName'
        And response body contains 'length must be between'