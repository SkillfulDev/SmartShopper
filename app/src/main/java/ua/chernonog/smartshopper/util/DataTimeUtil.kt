package ua.chernonog.smartshopper.util

import android.content.SharedPreferences
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

const val DEFAULT_TIME_FORMAT = "hh:mm - yyyy/MM/dd"

object DataTimeUtil {
    fun getCurrentTime(): String {
        val formatter = SimpleDateFormat(DEFAULT_TIME_FORMAT, Locale.getDefault())
        return formatter.format(Calendar.getInstance().time)
    }

    fun getTimeFormat(time: String, preferences: SharedPreferences): String {
        val formatter = SimpleDateFormat(DEFAULT_TIME_FORMAT, Locale.getDefault())
        val date = formatter.parse(time)
        val newFormat = preferences.getString("time_format_key", DEFAULT_TIME_FORMAT)
        val newFormatter = SimpleDateFormat(newFormat, Locale.getDefault())
        return if (date != null) {
            newFormatter.format(date)
        } else {
            time
        }
    }
}
