package com.internship.data.mapper

import com.internship.data.local.dao.CameraDao
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