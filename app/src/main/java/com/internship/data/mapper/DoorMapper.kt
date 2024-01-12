package com.internship.data.mapper

import com.internship.data.local.dao.DoorDao
import com.internship.data.remote.dto.DoorDto
import com.internship.domain.model.Door

fun DoorDao.toDoor(): Door {
    return Door(
        id = this.id,
        name = this.name,
        image = this.image,
        room = this.room,
        isFavourite = this.isFavourite,
        isOpened = this.isOpened
    )
}

fun DoorDto.toDoorDao(): DoorDao {
    return DoorDao().apply {
        id = this@toDoorDao.id
        name = this@toDoorDao.name
        image = this@toDoorDao.snapshot
        room = this@toDoorDao.room
        isFavourite = this@toDoorDao.favorites
    }
}