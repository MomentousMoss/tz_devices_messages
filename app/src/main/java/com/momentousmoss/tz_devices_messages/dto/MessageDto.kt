package com.momentousmoss.tz_devices_messages.dto

data class MessageDto(
    var id: Int? = null,
    var time: String,
    var date: String,
    var author: String,
    var text: String
)
