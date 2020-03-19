package com.raphau.licho.util

import android.text.format.DateUtils
import java.text.DateFormat
import java.util.*

fun Calendar.toDateString(): String {
    return DateUtils.formatSameDayTime(timeInMillis,
        System.currentTimeMillis(),
        DateFormat.SHORT,
        DateFormat.SHORT).toString()
}