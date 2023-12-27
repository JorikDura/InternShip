package com.internship.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CamerasResponseDto(
    @SerialName("success")
    val success: Boolean,
    @SerialName("data")
    val data: CameraDataDto
)