package com.internship.domain.repository

import com.internship.domain.model.Camera
import com.internship.domain.model.Door

interface InternRepository {

    suspend fun getCams(): List<Camera>
    suspend fun getDoors(): List<Door>

}