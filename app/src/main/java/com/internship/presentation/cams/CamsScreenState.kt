package com.internship.presentation.cams

import com.internship.domain.model.Camera

data class CamsScreenState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isError: Boolean = false,
    val cams: List<Camera> = listOf(),
    val rooms: List<String> = listOf(),
    val errorMessage: String = ""
)