package com.internship.presentation.doors

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.internship.domain.model.Door
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class DoorScreenViewModel @Inject constructor() : ViewModel() {

    private val _isRefreshing = MutableLiveData(false)
    val isRefreshing: LiveData<Boolean> get() = _isRefreshing

    private val _doors = MutableLiveData<List<Door>>(listOf())
    val doors: LiveData<List<Door>> get() = _doors

    init {
        val testValues = mutableListOf<Door>().apply {
            repeat(5) {
                add(
                    Door(
                        id = it,
                        name = "Door $it",
                        image = null,
                        room = "Room $it",
                        isFavourite = Random.nextBoolean(),
                        isOpened = Random.nextBoolean()
                    )
                )
            }
            add(
                Door(
                    id = 5,
                    name = "Door 5",
                    image = "",
                    room = "Room 5",
                    isFavourite = Random.nextBoolean(),
                    isOpened = Random.nextBoolean()
                )
            )
        }
        _doors.value = testValues
    }

    fun onEvent(event: DoorScreenEvents) {
        when (event) {
            is DoorScreenEvents.Edit -> {

            }

            is DoorScreenEvents.Favourite -> {

            }

            is DoorScreenEvents.UnFavourite -> {

            }

            is DoorScreenEvents.Close -> {

            }

            is DoorScreenEvents.Open -> {

            }

            DoorScreenEvents.Update -> {
                viewModelScope.launch {
                    _isRefreshing.value = true
                    delay(5000L)
                    _isRefreshing.value = false
                }
            }
        }
    }
}