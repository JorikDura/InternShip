package com.internship.presentation.cams

sealed class CamsScreenEvents {
    data class Favourite(val id: Int): CamsScreenEvents()
    data class UnFavourite(val id: Int): CamsScreenEvents()
    data object Update: CamsScreenEvents()
}