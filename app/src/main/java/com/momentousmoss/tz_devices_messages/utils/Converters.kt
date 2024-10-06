package com.momentousmoss.tz_devices_messages.utils

import android.database.Cursor
import com.momentousmoss.tz_devices_messages.dto.DeviceDto

fun Cursor.toDeviceDto() = DeviceDto(
    id = getInt(0),
    name = getString(1),
    type = getString(2),
    status = getString(3),
    mac = getString(4),
    subscriptions = getString(5)
)