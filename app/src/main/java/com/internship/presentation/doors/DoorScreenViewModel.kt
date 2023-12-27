package com.internship.presentation.doors

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.internship.domain.model.Door
import com.internship.domain.use_case.GetDoorsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoorScreenViewModel @Inject constructor(
    private val getDoorsUseCase: GetDoorsUseCase
) : ViewModel() {

    private val _isRefreshing = MutableLiveData(false)
    val isRefreshing: LiveData<Boolean> get() = _isRefreshing

    private val _doors = MutableLiveData<List<Door>>(listOf())
    val doors: LiveData<List<Door>> get() = _doors

    init {
        loadData()
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
                _isRefreshing.value = true
                loadData(fetchFromRemote = true)
                _isRefreshing.value = false
            }
        }
    }

    private fun loadData(fetchFromRemote: Boolean = false) {
        viewModelScope.launch {
            val data = getDoorsUseCase(fetchFromRemote)
            _doors.value = data
        }
    }
}