package com.momentousmoss.tz_devices_messages.utils

import android.database.Cursor
import com.momentousmoss.tz_devices_messages.dto.DeviceDto
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

val TIME_FORMAT = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)
val DATE_FORMAT = SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH)

fun formatTime(currentDateTime: Date): String {
    return TIME_FORMAT.format(currentDateTime)
}

fun formatDate(currentDateTime: Date): String {
    return DATE_FORMAT.format(currentDateTime)
}

fun Cursor.toDeviceDto() = DeviceDto(
    id = getInt(0),
    name = getString(1),
    type = getString(2),
    status = getString(3),
    mac = getString(4),
    subscriptions = getString(5)
)