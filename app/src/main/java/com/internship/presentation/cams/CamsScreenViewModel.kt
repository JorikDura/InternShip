package com.internship.presentation.cams

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.internship.domain.model.Camera
import com.internship.domain.use_case.GetCamsUseCase
import com.internship.domain.use_case.SetFavouriteCamUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CamsScreenViewModel @Inject constructor(
    private val getCamsUseCase: GetCamsUseCase,
    private val setFavouriteCamUseCase: SetFavouriteCamUseCase
) : ViewModel() {

    private val _isRefreshing = MutableLiveData(false)
    val isRefreshing: LiveData<Boolean> get() = _isRefreshing

    private val _cams = MutableLiveData(listOf<Camera>())
    val cams: LiveData<List<Camera>> get() = _cams

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
                    _isRefreshing.value = true
                    loadData(fetchFromRemote = true)
                    _isRefreshing.value = false
                }
            }
        }
    }

    private fun loadData(fetchFromRemote: Boolean = false) {
        viewModelScope.launch {
            val data = getCamsUseCase(fetchFromRemote)
            _cams.value = data
        }
    }

}