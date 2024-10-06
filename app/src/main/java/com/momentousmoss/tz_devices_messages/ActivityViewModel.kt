package com.momentousmoss.tz_devices_messages

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

class ActivityViewModel : ViewModel() {

    val selectedNavigationTitleState : MutableState<String?> = mutableStateOf(null)

    fun updateSelectedNavigationTitleState(value: String) {
        selectedNavigationTitleState.value = value
    }

    fun navigationClick(navController: NavController, destinationId: Int, titleString: String) {
        navController.apply {
            navigate(destinationId)
            updateSelectedNavigationTitleState(titleString)
        }
    }

}