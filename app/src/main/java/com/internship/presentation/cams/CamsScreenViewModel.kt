package com.internship.presentation.cams

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.internship.domain.model.Camera
import com.internship.domain.use_case.GetCamsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CamsScreenViewModel @Inject constructor(
    private val getCamsUseCase: GetCamsUseCase
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
                val newList = _cams.value?.toMutableList()
                newList?.let {
                    val item = newList.find {
                        it.id == event.id
                    }

                    val index = newList.indexOf(item)
                    newList[index].isFavourite = true
                    _cams.value = newList
                }
            }

            is CamsScreenEvents.UnFavourite -> {
                val newList = _cams.value?.toMutableList()
                newList?.let {
                    val item = newList.find {
                        it.id == event.id
                    }

                    val index = newList.indexOf(item)
                    newList[index].isFavourite = false
                    _cams.value = newList
                }
            }

            CamsScreenEvents.Update -> {
                viewModelScope.launch {
                    _isRefreshing.value = true
                    val test = getCamsUseCase()
                    _cams.value = test
                    _isRefreshing.value = false
                }
            }
        }
    }
    private fun loadData() {
        viewModelScope.launch {
            val remoteData = getCamsUseCase()
            _cams.value = remoteData
        }
    }

}