package edu.iesam.gametracker.features.recommend.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.iesam.gametracker.app.domain.ErrorApp
import edu.iesam.gametracker.features.videogames.domain.GetRecommendedVideogamesUseCase
import edu.iesam.gametracker.features.videogames.domain.GetVideogamesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class RecommendedViewModel(
    private val getRecommendedVideogamesUseCase: GetRecommendedVideogamesUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> get() = _uiState

    fun loadRecommendations() {
        _uiState.postValue(UiState(isLoading = true))
        viewModelScope.launch(Dispatchers.IO) {
            val result = getRecommendedVideogamesUseCase()
            _uiState.postValue(
                UiState(
                    videogames = result.getOrNull(),
                    errorApp = result.exceptionOrNull() as? ErrorApp
                )
            )
        }
    }

    data class UiState(
        val isLoading: Boolean = false,
        val errorApp: ErrorApp? = null,
        val videogames: List<GetVideogamesUseCase.VideoGameFeed>? = null
    )
}
