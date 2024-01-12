package com.internship.presentation.doors

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.internship.domain.use_case.EditDoorNameUseCase
import com.internship.domain.use_case.GetDoorsUseCase
import com.internship.domain.use_case.SetFavouriteDoorUseCase
import com.internship.domain.use_case.SetLockToDoorUseCase
import com.internship.utils.Resource
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

    private val _state = MutableLiveData(DoorScreenState(isLoading = true))
    val state: LiveData<DoorScreenState> get() = _state

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
                _state.value = _state.value?.copy(isRefreshing = true)
                loadData(fetchFromRemote = true)
            }
        }
    }

    private fun loadData(fetchFromRemote: Boolean = false) {
        viewModelScope.launch {
            val result = getDoorsUseCase(fetchFromRemote)
            when (result) {
                is Resource.Error -> {
                    _state.value = _state.value?.copy(
                        isLoading = false,
                        isRefreshing = false,
                        isError = true,
                        errorMessage = result.message ?: ""
                    )
                }

                is Resource.Success -> {
                    result.data?.let { doors ->
                        _state.value = _state.value?.copy(
                            isLoading = false,
                            isRefreshing = false,
                            isError = false,
                            doors = doors
                        )
                    }
                }
            }
        }
    }
}