package com.example.issue_tracker.domain.model

enum class IssueState (val value:String){
    OPEN("열린이슈"),
    WRITE_MYSELF("내가 작성한 이슈"),
    ASSIGN_MYSELF("나에게 할당된 이슈"),
    WRITE_COMMENT("내가 댓글을 남긴 이슈"),
    CLOSE("닫힌 이슈")
}