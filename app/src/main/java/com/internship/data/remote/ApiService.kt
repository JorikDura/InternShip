package com.internship.data.remote

import com.internship.data.remote.dto.CamerasResponseDto
import com.internship.data.remote.dto.DoorResponseDto
import com.internship.utils.Resource

interface ApiService {

    suspend fun getCams(): Resource<CamerasResponseDto>
    suspend fun getDoors(): Resource<DoorResponseDto>
}