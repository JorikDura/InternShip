package com.internship.presentation.cams

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.internship.domain.use_case.GetCamsUseCase
import com.internship.domain.use_case.GetRoomsUseCase
import com.internship.domain.use_case.SetFavouriteCamUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CamsScreenViewModel @Inject constructor(
    private val getCamsUseCase: GetCamsUseCase,
    private val getRoomsUseCase: GetRoomsUseCase,
    private val setFavouriteCamUseCase: SetFavouriteCamUseCase
) : ViewModel() {

    private val _state = MutableLiveData(CamsScreenState(isLoading = true))
    val state: LiveData<CamsScreenState> get() = _state

    init {
        loadData()
    }

    fun onEvent(event: CamsScreenEvents) {
        when (event) {
            is CamsScreenEvents.Favourite -> {
                viewModelScope.launch {
                    setFavouriteCamUseCase(event.id, true)
                    loadData()
                }
            }

            is CamsScreenEvents.UnFavourite -> {
                viewModelScope.launch {
                    setFavouriteCamUseCase(event.id, false)
                    loadData()
                }
            }

            CamsScreenEvents.Update -> {
                viewModelScope.launch {
                    _state.value = _state.value?.copy(isRefreshing = true)
                    loadData(fetchFromRemote = true)
                }
            }
        }
    }

    private fun loadData(fetchFromRemote: Boolean = false) {
        viewModelScope.launch {
            _state.value = _state.value?.copy(
                isLoading = false,
                isRefreshing = false,
                cams = getCamsUseCase(fetchFromRemote),
                rooms = getRoomsUseCase(fetchFromRemote)
            )
        }
    }

}