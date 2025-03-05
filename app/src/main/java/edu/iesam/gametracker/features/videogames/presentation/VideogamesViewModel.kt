package edu.iesam.gametracker.features.videogames.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.iesam.gametracker.app.domain.ErrorApp
import edu.iesam.gametracker.features.videogames.domain.GetVideogamesUseCase
import edu.iesam.gametracker.features.videogames.domain.Videogame
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class VideogamesViewModel(private val getVideogamesUseCase: GetVideogamesUseCase) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> get() = _uiState

    fun videogamesCreated() {
        _uiState.value = UiState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val videogames = getVideogamesUseCase.invoke()
            _uiState.postValue(
                UiState(
                    videogame = videogames.getOrNull(),
                    errorApp = videogames.exceptionOrNull() as ErrorApp?
                )
            )
        }
    }


    data class UiState(
        val isLoading: Boolean = false,
        val errorApp: ErrorApp? = null,
        val videogame: List<Videogame>? = null
    )
}