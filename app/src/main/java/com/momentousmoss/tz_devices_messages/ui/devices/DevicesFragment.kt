package com.momentousmoss.tz_devices_messages.ui.devices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.momentousmoss.tz_devices_messages.dto.DeviceDto
import com.momentousmoss.tz_devices_messages.enums.DeviceParametersEnum
import com.momentousmoss.tz_devices_messages.enums.DeviceStatusEnum
import org.koin.androidx.viewmodel.ext.android.viewModel

class DevicesFragment : Fragment() {

    private val devicesViewModel by viewModel<DevicesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        devicesViewModel.init()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            SetFragmentView()
        }
    }

    @Composable
    fun SetFragmentView() {
        val deviceSelected = remember { devicesViewModel.deviceStateSelected }
        val deviceFilterSelected by remember { devicesViewModel.deviceFilterStateSelected }
        Column {
            SetTitle()
            SetDivider(32)
            SetFilterList(deviceFilterSelected)
            SetDivider(0)
            SetDevicesList()
            SetDivider(8)
            SetSelectedDeviceInfoView(deviceSelected)
        }
    }


    @Composable
    fun SetTitle() {
        Text(
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            ),
            text = "Devices List"
        )
    }

    @Composable
    fun SetFilterList(deviceFilterSelected: String) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            DeviceStatusEnum.entries.forEach {
                item { FilterItem(it.valueText, deviceFilterSelected) }
            }
        }
    }

    @Composable
    private fun FilterItem(filterValue: String, deviceFilterSelected: String) {
        SuggestionChip(
            onClick = {
                devicesViewModel.updateSelectedFilter(filterValue)
            },
            label = {
                Text(
                    color = if (filterValue == deviceFilterSelected) Color.Blue else Color.Black,
                    text = filterValue
                )
            }
        )
    }

    @Composable
    fun SetDevicesList() {
        val devicesList = remember { devicesViewModel.devicesStateList }
        SetDevicesListTitle()
        devicesList.forEach {
            SetDevicesListItem(it)
        }
    }

    @Composable
    fun SetDevicesListTitle() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val deviceTitleItemModifier = Modifier.weight(1f)
            DeviceParametersEnum.entries.forEach {
                DevicesListTitleItem(it.valueText, deviceTitleItemModifier)
            }
        }
    }

    @Composable
    fun DevicesListTitleItem(titleText: String, modifier: Modifier) {
        Text(
            text = titleText,
            style = TextStyle(fontSize = 12.sp, color = Color.Black, fontWeight = FontWeight.Bold),
            modifier = modifier
        )
    }

    @Composable
    fun SetDevicesListItem(device: DeviceDto) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                devicesViewModel.updateSelectedDeviceState(device)
            }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                val deviceItemModifier = Modifier.weight(1f)
                DevicesListItem(device.name, deviceItemModifier)
                DevicesListItem(device.type, deviceItemModifier)
                DevicesListItem(device.status, deviceItemModifier)
                DevicesListItem(device.mac, deviceItemModifier)
                DevicesListItem(device.subscriptions, deviceItemModifier)
            }
        }
    }

    @Composable
    fun DevicesListItem(valueText: String, modifier: Modifier) {
        Text(
            text = valueText,
            style = TextStyle(fontSize = 10.sp, color = Color.Black),
            modifier = modifier
        )
    }

    @Composable
    fun SetSelectedDeviceInfoView(deviceSelected: MutableState<DeviceDto?>) {
        Column(
            modifier = Modifier.padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            SelectedDeviceInfo(deviceSelected)
            SuggestionChip(
                onClick = {},
                label = {
                    Text(
                        color = Color.Black,
                        text = "Send"
                    )
                }
            )
        }
    }

    @Composable
    fun SelectedDeviceInfo(deviceSelected: MutableState<DeviceDto?>) {
        deviceSelected.value.let {
            SelectedDeviceInfoRow(DeviceParametersEnum.NAME.valueText, it?.name)
            SelectedDeviceInfoRow(DeviceParametersEnum.TYPE.valueText, it?.type)
            SelectedDeviceInfoRow(DeviceParametersEnum.STATUS.valueText, it?.status)
            SelectedDeviceInfoRow(DeviceParametersEnum.MAC.valueText, it?.mac)
            SelectedDeviceInfoRow(DeviceParametersEnum.SUBSCRIPTIONS.valueText, it?.subscriptions)
        }
    }

    @Composable
    fun SelectedDeviceInfoRow(title: String, value: String?) {
        Row {
            Text(
                text = "$title: ",
                style = TextStyle(fontSize = 16.sp, color = Color.Black, fontWeight = FontWeight.Bold)
            )
            value?.let {
                Text(
                    text = it,
                    style = TextStyle(fontSize = 16.sp, color = Color.Blue)
                )
            }
        }
    }

    @Composable
    fun SetDivider(horizontalPadding: Int) {
        HorizontalDivider(
            color = Color.Black,
            modifier = Modifier.padding(horizontal = horizontalPadding.dp, vertical = 8.dp)
        )
    }

}