package com.internship.presentation.cams

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.internship.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CamsScreen() {
    val viewModel: CamsScreenViewModel = hiltViewModel()
    val cams = viewModel.cams.observeAsState(listOf())
    val rooms = viewModel.rooms.observeAsState(listOf())
    val isRefreshing = viewModel.isRefreshing.observeAsState(false)
    val pullRefreshState = rememberPullRefreshState(
        isRefreshing.value, { viewModel.onEvent(CamsScreenEvents.Update) })
    Box(
        modifier = Modifier
            .pullRefresh(pullRefreshState)
    ) {
        LazyColumn {
            rooms.value.forEach { room ->
                val roomCams = cams.value.filter {
                    it.room == room
                }

                if (roomCams.isNotEmpty()) {
                    item {
                        Text(
                            modifier = Modifier
                                .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                                .fillMaxWidth(),
                            text = room,
                            fontSize = 20.sp
                        )
                    }

                    items(roomCams, key = { it.id }) { camera ->
                        CameraItem(
                            camera = camera,
                            eventListener = { event ->
                                viewModel.onEvent(event)
                            }
                        )
                    }
                }

            }
            val unknownPlaceRooms = cams.value.filter {
                it.room == null
            }

            if (unknownPlaceRooms.isNotEmpty()) {
                item {
                    Text(
                        modifier = Modifier
                            .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                            .fillMaxWidth(),
                        text = stringResource(id = R.string.unknown),
                        fontSize = 20.sp
                    )
                }
                items(unknownPlaceRooms, key = { it.id }) { camera ->
                    CameraItem(
                        camera = camera,
                        eventListener = { event ->
                            viewModel.onEvent(event)
                        }
                    )
                }
            }
        }
        PullRefreshIndicator(
            refreshing = isRefreshing.value,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
            contentColor = MaterialTheme.colorScheme.primary,
            backgroundColor = MaterialTheme.colorScheme.background
        )
    }

}