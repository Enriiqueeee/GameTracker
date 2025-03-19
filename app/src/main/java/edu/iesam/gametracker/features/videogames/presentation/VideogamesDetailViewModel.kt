package edu.iesam.gametracker.features.videogames.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.iesam.gametracker.app.domain.ErrorApp
import edu.iesam.gametracker.features.videogames.domain.GetVideogameDetailUseCase
import edu.iesam.gametracker.features.videogames.domain.Videogame
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class VideogameDetailViewModel(private val getVideogameDetailUseCase: GetVideogameDetailUseCase) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> get() = _uiState

    fun loadVideogameDetail(videogameId: Int) {
        _uiState.value = UiState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val result = getVideogameDetailUseCase.invoke(videogameId)
            _uiState.postValue(
                UiState(
                    videogame = result.getOrNull(),
                    errorApp = result.exceptionOrNull() as? ErrorApp
                )
            )
        }
    }

    data class UiState(
        val isLoading: Boolean = false,
        val errorApp: ErrorApp? = null,
        val videogame: Videogame? = null
    )
}
