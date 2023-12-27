package com.internship.domain.model

data class Door(
    val id: Int,
    val name: String,
    val image: String?,
    val room: String?,
    val isFavourite: Boolean,
    val isOpened: Boolean = false,
)