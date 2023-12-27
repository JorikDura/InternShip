package com.internship.data.repository

import com.internship.data.mapper.toCamera
import com.internship.data.mapper.toDoor
import com.internship.data.remote.ApiService
import com.internship.domain.model.Camera
import com.internship.domain.model.Door
import com.internship.domain.repository.InternRepository
import javax.inject.Inject

class InternRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : InternRepository {

    override suspend fun getCams(): List<Camera> {
        return apiService.getCams()?.data?.camera?.map { it.toCamera() } ?: emptyList()
    }

    override suspend fun getDoors(): List<Door> {
        return apiService.getDoors()?.data?.map { it.toDoor() } ?: emptyList()
    }
}