package com.internship.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DoorResponseDto(
    @SerialName("data")
    val `data`: List<DoorDto>,
    @SerialName("success")
    val success: Boolean
)