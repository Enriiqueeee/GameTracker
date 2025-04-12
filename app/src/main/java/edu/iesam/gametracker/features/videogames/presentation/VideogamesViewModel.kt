package edu.iesam.gametracker.features.videogames.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.iesam.gametracker.app.domain.ErrorApp
import edu.iesam.gametracker.features.videogames.domain.GetFavoriteVideogamesUseCase
import edu.iesam.gametracker.features.videogames.domain.GetVideogamesUseCase
import edu.iesam.gametracker.features.videogames.domain.ToggleFavoriteVideogameUseCase
import edu.iesam.gametracker.features.videogames.domain.Videogame
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import org.koin.core.context.loadKoinModules

@KoinViewModel
class VideogamesViewModel(
    private val getVideogamesUseCase: GetVideogamesUseCase,
    private val getFavoriteVideogamesUseCase: GetFavoriteVideogamesUseCase,
    private val toggleFavoriteVideogameUseCase: ToggleFavoriteVideogameUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> get() = _uiState

    fun loadGames() {
        _uiState.postValue(UiState(isLoading = true))
        viewModelScope.launch(Dispatchers.IO) {
            val videogames = getVideogamesUseCase.invoke()
            _uiState.postValue(
                UiState(
                    videogames = videogames.getOrNull(),
                    errorApp = videogames.exceptionOrNull() as ErrorApp?
                )
            )
        }
    }


    fun loadFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getFavoriteVideogamesUseCase()
            _uiState.postValue(
                UiState(
                    videogames = result.getOrNull(),
                    errorApp = result.exceptionOrNull() as ErrorApp?
                )
            )
        }
    }

    fun toggleFavorite(videogame: Videogame, isFavoriteView: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            toggleFavoriteVideogameUseCase(videogame)
            if (isFavoriteView) {
                loadFavorites()
            } else {
                loadGames()
            }
        }
    }
}

data class UiState(
    val isLoading: Boolean = false,
    val errorApp: ErrorApp? = null,
    val videogames: List<GetVideogamesUseCase.VideoGameFeed>? = null
)

