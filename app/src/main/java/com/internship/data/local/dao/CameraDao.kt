package com.internship.data.local.dao

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class CameraDao : RealmObject {
    @PrimaryKey
    var id: Int = -1
    var name: String = ""
    var image: String? = ""
    var room: String? = ""
    var isFavourite: Boolean = false
    var isRec: Boolean = false
}