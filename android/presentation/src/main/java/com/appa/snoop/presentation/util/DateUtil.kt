package com.appa.snoop.presentation.util

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Date
import java.util.Locale

object DateUtil {
    private const val MINUTE = 60L
    private const val HOUR = 3600L
    private const val DAY = 86400L
    private const val MONTH = 2592000L
    private const val YEAR = 31536000L

    @RequiresApi(Build.VERSION_CODES.O)
    fun dateToString(timeString: String): String {
        val time = timeString.slice(0..18)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val dateTime = LocalDateTime.parse(time, formatter)

        val currentTime = LocalDateTime.now()
        val timeDifference = ChronoUnit.SECONDS.between(dateTime, currentTime)

        return when {
            timeDifference < MINUTE -> "방금 전"
            timeDifference < HOUR -> "${timeDifference / MINUTE}분 전"
            timeDifference < DAY -> "${timeDifference / HOUR}시간 전"
            timeDifference < MONTH -> "${timeDifference / DAY}일 전"
            timeDifference < YEAR -> "${timeDifference / MONTH}달 전"
            else -> "${timeDifference / YEAR}년 전"
        }
    }

    fun formatDate(input: String, formatType: String): String {
        // 원래 날짜 형식 정의
        val originalFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

        // formatType에 따른 대상 날짜 형식 정의
        val targetFormat = when (formatType) {
            "day", "week" -> SimpleDateFormat("MM-dd", Locale.getDefault())
            "hour" -> SimpleDateFormat("MM-dd HH:mm", Locale.getDefault())
            else -> throw IllegalArgumentException("Unsupported formatType: $formatType")
        }

        // 원래 형식으로 날짜를 파싱
        val date = originalFormat.parse(input)
        // 원하는 형식으로 날짜를 포맷팅
        return date.let { targetFormat.format(it) }
    }

    fun convertMillisToYymmdd(millis: Long): String {
        val dateFormat = SimpleDateFormat("yy.MM.dd")
        val date = Date(millis)
        return dateFormat.format(date)
    }

}