package com.internship.data.mapper

import com.internship.data.local.dao.CameraDao
import com.internship.data.remote.dto.CameraDto
import com.internship.domain.model.Camera

fun CameraDao.toCamera(): Camera {
    return Camera(
        id = this.id,
        name = this.name,
        image = this.image,
        room = this.room,
        isFavourite = this.isFavourite,
        isRec = this.isRec,
    )
}

fun CameraDto.toCameraDao(): CameraDao {
    return CameraDao().apply {
        id = this@toCameraDao.id
        name = this@toCameraDao.name
        image = this@toCameraDao.snapshot
        room = this@toCameraDao.room
        isFavourite = this@toCameraDao.favorites
        isRec = this@toCameraDao.rec
    }
}