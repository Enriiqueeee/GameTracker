package edu.iesam.gametracker.features.videogames.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.iesam.gametracker.app.domain.ErrorApp
import edu.iesam.gametracker.features.videogames.domain.GetFavoriteVideogamesUseCase
import edu.iesam.gametracker.features.videogames.domain.GetVideogamesUseCase
import edu.iesam.gametracker.features.videogames.domain.RemoveFavoriteVideogameUseCase
import edu.iesam.gametracker.features.videogames.domain.SaveFavoriteVideogameUseCase
import edu.iesam.gametracker.features.videogames.domain.Videogame
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class VideogamesViewModel(private val getVideogamesUseCase: GetVideogamesUseCase, private val getFavoriteVideogamesUseCase: GetFavoriteVideogamesUseCase, private val removeFavoriteVideogameUseCase: RemoveFavoriteVideogameUseCase, private val saveFavoriteVideogameUseCase: SaveFavoriteVideogameUseCase) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> get() = _uiState

    private val _favorites = MutableLiveData<List<Videogame>>()
    val favorites: LiveData<List<Videogame>> get() = _favorites

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

    fun saveFavorite(videogame: Videogame) {
        viewModelScope.launch(Dispatchers.IO) {
            saveFavoriteVideogameUseCase(videogame)
            loadFavorites()
        }
    }

    fun removeFavorite(videogame: Videogame) {
        viewModelScope.launch(Dispatchers.IO) {
            removeFavoriteVideogameUseCase(videogame)
            loadFavorites()
        }
    }

    fun loadFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            val favs = getFavoriteVideogamesUseCase()
            _favorites.postValue(favs)
        }
    }


    data class UiState(
        val isLoading: Boolean = false,
        val errorApp: ErrorApp? = null,
        val videogame: List<Videogame>? = null
    )
}