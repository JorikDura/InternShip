package com.internship.presentation.cams

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CamsScreen() {
    val viewModel: CamsScreenViewModel = hiltViewModel()
    val cams = viewModel.cams.observeAsState(listOf())
    val isRefreshing = viewModel.isRefreshing.observeAsState(false)
    val pullRefreshState = rememberPullRefreshState(
        isRefreshing.value, { viewModel.onEvent(CamsScreenEvents.Update) })
    Box(
        modifier = Modifier
            .pullRefresh(pullRefreshState)
    ) {
        LazyColumn {
            items(items = cams.value, key = { it.id }) { camera ->
                CameraItem(
                    camera = camera,
                    eventListener = { event ->
                        viewModel.onEvent(event)
                    }
                )
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