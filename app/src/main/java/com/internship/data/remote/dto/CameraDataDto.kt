package com.internship.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CameraDataDto(
    @SerialName("room")
    val room: List<String>,
    @SerialName("cameras")
    val camera: List<CameraDto>
)