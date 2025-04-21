package edu.iesam.gametracker.features.setting.presentation.developer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.iesam.gametracker.app.domain.ErrorApp
import edu.iesam.gametracker.features.setting.domain.developer.Developer
import edu.iesam.gametracker.features.setting.domain.developer.GetDeveloperUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class DeveloperViewModel(
    private val getDevelopersUseCase: GetDeveloperUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> = _uiState

    fun loadDevelopers() {
        _uiState.value = UiState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val developers = getDevelopersUseCase()
            if (developers.getOrNull() != null) {
                _uiState.postValue(UiState(developers = developers.getOrNull()))
            } else {
                _uiState.postValue(UiState(errorApp = developers.exceptionOrNull() as ErrorApp?))
            }
        }
    }

    data class UiState(
        val isLoading: Boolean = false,
        val errorApp: ErrorApp? = null,
        val developers: List<Developer>? = null
    )
}