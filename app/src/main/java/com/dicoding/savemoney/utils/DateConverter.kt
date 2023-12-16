package com.dicoding.savemoney.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateConverter {
    @RequiresApi(Build.VERSION_CODES.O)
    fun convertDate(currentDate: Date?) : String {
        val calendar = Calendar.getInstance()
        calendar.time = currentDate
        val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        return sdf.format(calendar.time)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDate(currentDate: String?, targetTimeZone: String): String {
        val instparse = Instant.parse(currentDate)
        val format = DateTimeFormatter.ofPattern("dd MMM yyyy | HH:mm ")
            .withZone(ZoneId.of(targetTimeZone))
        return format.format(instparse)
    }
}