package com.momentousmoss.tz_devices_messages.ui

import com.momentousmoss.tz_devices_messages.ActivityViewModel
import com.momentousmoss.tz_devices_messages.ui.devices.DevicesViewModel
import com.momentousmoss.tz_devices_messages.ui.messages.MessagesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {

    viewModel { ActivityViewModel() }
    viewModel { DevicesViewModel() }
    viewModel { MessagesViewModel() }

}