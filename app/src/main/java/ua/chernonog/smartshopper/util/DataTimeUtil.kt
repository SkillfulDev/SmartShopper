package ua.chernonog.smartshopper.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object DataTimeUtil {
    fun getCurrentTime(): String {
        val formatter = SimpleDateFormat("hh:mm - yyyy/MM/dd", Locale.getDefault())
        return formatter.format(Calendar.getInstance().time)
    }
}
