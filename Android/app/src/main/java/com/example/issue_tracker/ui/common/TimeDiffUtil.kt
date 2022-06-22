package com.example.issue_tracker.ui.common


import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


const val SECONDS_OF_MINUTE = 60
const val SECONDS_OF_HOUR = 60 * 60
const val SECONDS_OF_DAY = 60 * 60 * 24

fun getTimeDiff(dateTimeInfo: String): String {
    val date = dateTimeInfo.replace("T", " ")
    val nowDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"))
    val dataFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val actionDateTime = LocalDateTime.parse(date, dataFormatter)
    val seconds = Duration.between(actionDateTime, nowDateTime).seconds.toInt()
    return secondsToActionLogTime(seconds)
}

fun secondsToActionLogTime(seconds: Int): String {
    return when {
        seconds > SECONDS_OF_DAY -> "${seconds / SECONDS_OF_DAY}일 전"
        seconds > SECONDS_OF_HOUR -> "${seconds / SECONDS_OF_HOUR}시간 전"
        seconds > SECONDS_OF_MINUTE -> "${seconds / SECONDS_OF_MINUTE}분 전"
        else -> "${seconds}초 전"
    }
}