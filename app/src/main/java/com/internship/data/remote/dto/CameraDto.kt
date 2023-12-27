package com.internship.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CameraDto(
    @SerialName("name")
    val name: String,
    @SerialName("snapshot")
    val snapshot: String,
    @SerialName("room")
    val room: String?,
    @SerialName("id")
    val id: Int,
    @SerialName("favorites")
    val favorites: Boolean,
    @SerialName("rec")
    val rec: Boolean
)