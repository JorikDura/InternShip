package com.internship.data.remote

import android.util.Log
import com.internship.data.remote.dto.CamerasResponseDto
import com.internship.data.remote.dto.DoorResponseDto
import com.internship.utils.Resource
import io.ktor.client.HttpClient
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(
    private val client: HttpClient
) : ApiService {
    override suspend fun getCams(): Resource<CamerasResponseDto> {
        return makeRequest<CamerasResponseDto> {
            client.get {
                url(HttpRoutes.CAMERAS)
            }
        }
    }

    override suspend fun getDoors(): Resource<DoorResponseDto> {
        return makeRequest<DoorResponseDto> {
            client.get {
                url(HttpRoutes.DOORS)
            }
        }
    }

    private suspend inline fun <reified T> makeRequest(block: () -> HttpResponse): Resource<T> {
        return try {
            val result = block().body<T>()
            Resource.Success(data = result)
        } catch (e: RedirectResponseException) {
            Log.e("RedirectResponseException", e.message)
            Resource.Error(message = e.response.status.description)
        } catch (e: ClientRequestException) {
            Log.e("ClientRequestException", e.message)
            Resource.Error(message = e.response.status.description)
        } catch (e: ServerResponseException) {
            Log.e("ServerResponseException", e.message)
            Resource.Error(message = e.response.status.description)
        } catch (e: NoTransformationFoundException) {
            Log.e("NoTransformationFoundException", e.message ?: "")
            Resource.Error(message = e.message ?: "")
        } catch (e: Exception) {
            Log.e("Exception", e.message ?: "")
            Resource.Error(message = e.message ?: "")
        }
    }
}
