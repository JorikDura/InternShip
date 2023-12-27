package com.internship.data.mapper

import com.internship.data.remote.dto.DoorDto
import com.internship.domain.model.Door

fun DoorDto.toDoor(): Door {
    return Door(
        id = this.id,
        name = this.name,
        image = this.snapshot,
        room = this.room,
        isFavourite = this.favorites
    )
}