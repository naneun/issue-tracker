package com.team03.issuetracker.issue.domain;

public enum IssueState {

    OPEN, CLOSE;

    public static IssueState nextState(IssueState state) {
        return IssueState.values()[state.ordinal() + 1 % values().length];
    }
}
