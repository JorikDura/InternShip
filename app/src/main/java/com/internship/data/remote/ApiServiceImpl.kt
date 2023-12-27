package com.internship.data.remote

import com.internship.data.remote.dto.CamerasResponseDto
import com.internship.data.remote.dto.DoorResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(
    private val client: HttpClient
) : ApiService {
    override suspend fun getCams(): CamerasResponseDto? {
        return try {
            client.get {
                url(HttpRoutes.CAMERAS)
            }.body()
        } catch (e: Exception) {
            println(e.message)
            null
        }
    }

    override suspend fun getDoors(): DoorResponseDto? {
        return try {
            client.get {
                url(HttpRoutes.DOORS)
            }.body()
        } catch (e: Exception) {
            println(e.message)
            null
        }
    }
}