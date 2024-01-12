package com.internship.presentation.cams

import com.internship.domain.model.Camera

data class CamsScreenState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isError: Boolean = false,
    val rooms: List<String> = listOf(),
    val cams: MutableMap<String?, List<Camera>> = mutableMapOf(),
    val errorMessage: String = ""
)