package com.namasake.animal.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.namasake.animal.domain.model.Bird
import com.namasake.animal.domain.model.Resource
import com.namasake.animal.domain.use_case.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCases: UseCases
):ViewModel() {

    sealed class AnimalEvent{
        class Success(val result: List<Bird>): AnimalEvent()
        class Failure(val errorText: String): AnimalEvent()
        object Loading : AnimalEvent()
        object Empty : AnimalEvent()
    }

    private val _dataState = MutableStateFlow<AnimalEvent>(AnimalEvent.Empty)
    val dataState: StateFlow<AnimalEvent> = _dataState


    fun getAnimals() {
        viewModelScope.launch {
            useCases.getAnimals().collect { response ->
                when(response){
                    is Resource.Success<*> ->{
                        val result = response.data

                        if (result == null){
                            _dataState.value = AnimalEvent.Failure("Something wrong")
                        }else{
                            _dataState.value = AnimalEvent.Success(result as List<Bird>)
                        }
                    }
                    is Resource.Error<*> ->
                    {
                        _dataState.value = AnimalEvent.Failure(errorText = "something wrong")
                    }
                }

            }
        }
    }
}


