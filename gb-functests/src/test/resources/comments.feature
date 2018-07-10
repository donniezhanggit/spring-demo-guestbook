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

    Scenario: Submit a new comment without anon name
        Given a comment input
        And no anon name
        And message is 'Hack this world!'
        When I submit the comment input
        Then response has status BAD_REQUEST
        And response body contains 'EMPTY_AUTHOR'
        And response body contains 'Can not create new comment without author\'s name'

    Scenario: Submit a new comment with empty message
        Given a comment input
        And anon name is 'Foo'
        And message is ''
        When I submit the comment input
        Then response has status PRECONDITION_FAILED
        And response body contains 'message'
        And response body contains 'length must be between'