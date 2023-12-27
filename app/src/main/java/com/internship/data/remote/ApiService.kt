package com.internship.data.remote

import com.internship.data.remote.dto.CamerasResponseDto
import com.internship.data.remote.dto.DoorResponseDto

interface ApiService {

    suspend fun getCams(): CamerasResponseDto?
    suspend fun getDoors(): DoorResponseDto?
}