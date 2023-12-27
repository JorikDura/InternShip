package com.internship.data.local.dao

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class DoorDao : RealmObject {
    @PrimaryKey
    var id: Int = -1
    var name: String = ""
    var image: String? = null
    var room: String? = null
    var isFavourite: Boolean = false
    var isOpened: Boolean = false
}