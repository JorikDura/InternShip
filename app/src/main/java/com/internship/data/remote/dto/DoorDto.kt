package com.internship.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DoorDto(
    @SerialName("favorites")
    val favorites: Boolean,
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("room")
    val room: String?,
    @SerialName("snapshot")
    val snapshot: String? = null
)