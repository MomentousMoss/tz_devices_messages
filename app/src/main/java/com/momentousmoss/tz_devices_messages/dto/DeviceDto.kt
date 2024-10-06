package com.momentousmoss.tz_devices_messages.dto

data class DeviceDto(
    var id: Int? = null,
    var name: String,
    var type: String,
    var status: String,
    var mac: String,
    var subscriptions: String
)
