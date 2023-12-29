package com.internship.presentation.cams

import com.internship.domain.model.Camera

data class CamsScreenState(
    var isLoading: Boolean = false,
    var isRefreshing: Boolean = false,
    var cams: List<Camera> = listOf(),
    var rooms: List<String> = listOf()
)