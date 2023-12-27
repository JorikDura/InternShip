package com.internship.domain.repository

import com.internship.domain.model.Camera
import com.internship.domain.model.Door

interface InternRepository {

    suspend fun getCams(fetchFromRemote: Boolean): List<Camera>
    suspend fun getDoors(fetchFromRemote: Boolean): List<Door>
    suspend fun setFavouriteCam(camId: Int, favourite: Boolean)

}