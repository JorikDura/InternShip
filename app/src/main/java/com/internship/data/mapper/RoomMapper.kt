package com.internship.data.mapper

import com.internship.data.local.dao.RoomDao

fun RoomDao.toRoomTitle(): String {
    return this.title
}