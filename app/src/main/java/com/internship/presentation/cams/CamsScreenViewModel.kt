package com.internship.presentation.cams

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.internship.domain.model.Camera
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class CamsScreenViewModel @Inject constructor() : ViewModel() {

    private val _isRefreshing = MutableLiveData(false)
    val isRefreshing: LiveData<Boolean> get() = _isRefreshing

    private val _cams = MutableLiveData(listOf<Camera>())
    val cams: LiveData<List<Camera>> get() = _cams

    private val _camsList = sortedSetOf<Camera>({ o1, o2 -> o1.id.compareTo(o2.id) })

    init {
        _camsList.apply {
            repeat(6) {
                add(
                    Camera(
                        id = it,
                        name = "Camera $it",
                        image = "",
                        room = "Room 1",
                        isFavourite = Random.nextBoolean(),
                        isRec = Random.nextBoolean()
                    )
                )
            }
        }
        updateList()
    }

    fun onEvent(event: CamsScreenEvents) {
        when (event) {
            is CamsScreenEvents.Favourite -> {
                val oldItem = _camsList.find {
                    it.id == event.id
                }
                val newItem = oldItem?.copy(isFavourite = true)
                newItem?.let {
                    _camsList.remove(oldItem)
                    _camsList.add(newItem)
                }
                updateList()
            }

            is CamsScreenEvents.UnFavourite -> {
                val oldItem = _camsList.find {
                    it.id == event.id
                }
                val newItem = oldItem?.copy(isFavourite = false)
                newItem?.let {
                    _camsList.remove(oldItem)
                    _camsList.add(newItem)
                }
                updateList()
            }

            CamsScreenEvents.Update -> {

            }
        }
    }

    private fun updateList() {
        _cams.value = _camsList.toList()
    }

}