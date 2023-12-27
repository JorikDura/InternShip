package com.internship.data.mapper

import com.internship.data.remote.dto.CameraDto
import com.internship.domain.model.Camera

fun CameraDto.toCamera(): Camera {
    return Camera(
        id = this.id,
        name = this.name,
        image = this.snapshot,
        room = this.room,
        isFavourite = this.favorites,
        isRec = this.rec,
    )
}