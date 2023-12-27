package com.internship.data.mapper

import com.internship.data.local.dao.DoorDao
import com.internship.domain.model.Door

fun DoorDao.toDoor(): Door {
    return Door(
        id = this.id,
        name = this.name,
        image = this.image,
        room = this.room,
        isFavourite = this.isFavourite
    )
}