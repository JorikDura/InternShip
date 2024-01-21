package com.internship.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseDto<T>(
    @SerialName("success")
    val success: Boolean,
    @SerialName("data")
    val data: T
)