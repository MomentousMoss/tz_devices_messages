package com.momentousmoss.tz_devices_messages.ui.devices

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.momentousmoss.tz_devices_messages.database.AppDatabase
import com.momentousmoss.tz_devices_messages.database.DevicesEntity
import com.momentousmoss.tz_devices_messages.dto.DeviceDto
import com.momentousmoss.tz_devices_messages.enums.DeviceStatusEnum
import com.momentousmoss.tz_devices_messages.enums.DeviceTypeEnum
import com.momentousmoss.tz_devices_messages.utils.toDeviceDto
import org.koin.java.KoinJavaComponent

class DevicesViewModel : ViewModel() {

    private val database by KoinJavaComponent.inject<AppDatabase>(AppDatabase::class.java)

    val devicesStateList = mutableStateListOf<DeviceDto>()
    val deviceFilterStateSelected = mutableStateOf(DeviceStatusEnum.ALL.valueText)
    val deviceStateSelected : MutableState<DeviceDto?> = mutableStateOf(null)

    fun init() {
        fillDevicesIfEmpty()
        getDevices()
    }

    private fun getDevices() {
        database.DevicesDao().getDevices().let {
            if (it.moveToFirst()) {
                do {
                    devicesStateList.add(it.toDeviceDto())
                } while (it.moveToNext())
            }
        }
    }

    fun updateSelectedFilter(filterValue: String) {
        updateSelectedDeviceState(null)
        updateSelectedFilterState(filterValue)
        devicesStateList.clear()
        if (filterValue == DeviceStatusEnum.ALL.valueText) {
            getDevices()
        } else {
            getDevicesByStatus()
        }
    }

    fun updateSelectedDeviceState(device: DeviceDto?) {
        deviceStateSelected.value = device
    }

    private fun updateSelectedFilterState(filterValue: String) {
        deviceFilterStateSelected.value = filterValue
    }

    private fun getDevicesByStatus() {
        database.DevicesDao().getDevicesByStatus(deviceFilterStateSelected.value).let {
            if (it.moveToFirst()) {
                do {
                    devicesStateList.add(it.toDeviceDto())
                } while (it.moveToNext())
            }
        }
    }

    //TODO пока так, будет время - добавить заполнение БД с фрагмента
    fun fillDevicesIfEmpty() {
        if (database.DevicesDao().getDevices().count < 1) {
            database.DevicesDao().insertDevice(
                DevicesEntity(
                    name = "Cam-5",
                    type = DeviceTypeEnum.CAMERA.valueText,
                    status = DeviceStatusEnum.LIVE.valueText,
                    mac = "ff:ff:ff:ff",
                    subscriptions = "SM-3"
                )
            )
            database.DevicesDao().insertDevice(
                DevicesEntity(
                    name = "Rep-1",
                    type = DeviceTypeEnum.CAMERA.valueText,
                    status = DeviceStatusEnum.DEAD.valueText,
                    mac = "ff:ff:ff:f1",
                    subscriptions = "SM-3"
                )
            )
            database.DevicesDao().insertDevice(
                DevicesEntity(
                    name = "LD-4",
                    type = DeviceTypeEnum.LIVEDATA.valueText,
                    status = DeviceStatusEnum.MUTE.valueText,
                    mac = "ff:ff:ff:f2",
                    subscriptions = "SM-3"
                )
            )
            database.DevicesDao().insertDevice(
                DevicesEntity(
                    name = "Dmitrii",
                    type = DeviceTypeEnum.PARTICIPANT.valueText,
                    status = DeviceStatusEnum.APPROVED.valueText,
                    mac = "ff:ff:ff:f3",
                    subscriptions = "no"
                )
            )
        }
    }

}