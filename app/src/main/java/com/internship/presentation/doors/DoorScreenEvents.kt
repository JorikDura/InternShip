package com.internship.presentation.doors

sealed class DoorScreenEvents {
    data class Favourite(val id: Int) : DoorScreenEvents()
    data class UnFavourite(val id: Int) : DoorScreenEvents()
    data class Edit(val newTitle: String) : DoorScreenEvents()
    data object Update : DoorScreenEvents()
    data class Open(val id: Int) : DoorScreenEvents()
    data class Close(val id: Int) : DoorScreenEvents()
}