package com.momentousmoss.tz_devices_messages

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.findNavController
import com.momentousmoss.tz_devices_messages.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val activityViewModel by viewModel<ActivityViewModel>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContent {
            SetActivityView()
        }
    }

    @Composable
    private fun SetActivityView() {
        Column {
            SetNavigationView()
            SetNavigationDivider()
            SetFragmentView()
        }
    }

    @Composable
    private fun SetNavigationView() {
        val selectedNavigationTitle by remember { activityViewModel.selectedNavigationTitleState }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 0.dp)
        ) {
            setDefaultSelectedNavigationTitleState()
            item {
                NavigationItem(
                    getString(R.string.navigation_title_home),
                    selectedNavigationTitle = selectedNavigationTitle
                )
            }
            item {
                NavigationItem(
                    getString(R.string.navigation_title_devices),
                    destinationId = R.id.toDevices,
                    selectedNavigationTitle = selectedNavigationTitle
                )
            }
            item {
                NavigationItem(
                    getString(R.string.navigation_title_apparatuses),
                    selectedNavigationTitle = selectedNavigationTitle
                )
            }
            item {
                NavigationItem(
                    getString(R.string.navigation_title_messages),
                    destinationId = R.id.toMessages,
                    selectedNavigationTitle = selectedNavigationTitle
                )
            }
            item {
                NavigationItem(
                    getString(R.string.navigation_title_statistics),
                    selectedNavigationTitle = selectedNavigationTitle
                )
            }
            item {
                NavigationItem(
                    getString(R.string.navigation_title_settings),
                    selectedNavigationTitle = selectedNavigationTitle
                )
            }
        }
    }

    private fun setDefaultSelectedNavigationTitleState() {
        activityViewModel.updateSelectedNavigationTitleState(
            findNavController(R.id.fragment_container).graph.findStartDestination().label.toString()
        )
    }

    @Composable
    private fun NavigationItem(
        titleString: String,
        destinationId: Int? = null,
        selectedNavigationTitle: String?
    ) {
        SuggestionChip(
            onClick = {
                destinationId?.let {
                    activityViewModel.navigationClick(
                        findNavController(R.id.fragment_container),
                        it,
                        titleString
                    )
                }
            },
            label = { Text(
                color = if (titleString != selectedNavigationTitle) Color.Black else Color.Blue,
                text = titleString
            ) }
        )
    }

    @Composable
    private fun SetNavigationDivider() {
        HorizontalDivider(
            color = Color.Black,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }

    @Composable
    private fun SetFragmentView() {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { binding.root }
        )
    }
}