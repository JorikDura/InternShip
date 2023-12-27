package com.internship.presentation.doors

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.internship.domain.model.Door
import com.internship.domain.use_case.EditDoorNameUseCase
import com.internship.domain.use_case.GetDoorsUseCase
import com.internship.domain.use_case.SetFavouriteDoorUseCase
import com.internship.domain.use_case.SetLockToDoorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoorScreenViewModel @Inject constructor(
    private val getDoorsUseCase: GetDoorsUseCase,
    private val setFavouriteDoorUseCase: SetFavouriteDoorUseCase,
    private val setLockToDoorUseCase: SetLockToDoorUseCase,
    private val editDoorNameUseCase: EditDoorNameUseCase
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
                viewModelScope.launch {
                    editDoorNameUseCase(event.id, event.newTitle)
                    loadData()
                }
            }

            is DoorScreenEvents.Favourite -> {
                viewModelScope.launch {
                    setFavouriteDoorUseCase(event.id, true)
                    loadData()
                }
            }

            is DoorScreenEvents.UnFavourite -> {
                viewModelScope.launch {
                    setFavouriteDoorUseCase(event.id, false)
                    loadData()
                }
            }

            is DoorScreenEvents.Close -> {
                viewModelScope.launch {
                    setLockToDoorUseCase(event.id, false)
                    loadData()
                }
            }

            is DoorScreenEvents.Open -> {
                viewModelScope.launch {
                    setLockToDoorUseCase(event.id, true)
                    loadData()
                }
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