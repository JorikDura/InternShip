package com.internship.data.local.dao

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class RoomDao : RealmObject {
    @PrimaryKey
    var id: Int = -1
    var title: String = ""
}