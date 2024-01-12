package com.internship.presentation.doors

import com.internship.domain.model.Door

data class DoorScreenState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isError: Boolean = false,
    val doors: List<Door> = listOf(),
    val errorMessage: String = ""
)
