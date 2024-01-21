package com.internship.data.remote

import com.internship.data.remote.dto.CameraDataDto
import com.internship.data.remote.dto.DoorDto
import com.internship.data.remote.dto.ResponseDto
import com.internship.utils.Resource

interface ApiService {

    suspend fun getCams(): Resource<ResponseDto<CameraDataDto>>
    suspend fun getDoors(): Resource<ResponseDto<List<DoorDto>>>
}