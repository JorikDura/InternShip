package com.internship.presentation.doors

import com.internship.domain.model.Door

data class DoorScreenState(
    var isLoading: Boolean = false,
    var isRefreshing: Boolean = false,
    var doors: List<Door> = listOf()
)
