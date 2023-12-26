package com.internship.domain.model

data class Camera(
    val id: Int,
    val name: String,
    val image: String?,
    val room: String?,
    val isFavourite: Boolean,
    val isRec: Boolean
)