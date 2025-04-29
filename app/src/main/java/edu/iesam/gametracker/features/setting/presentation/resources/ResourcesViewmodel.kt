package edu.iesam.gametracker.features.setting.presentation.resources

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.iesam.gametracker.app.domain.ErrorApp
import edu.iesam.gametracker.features.setting.domain.developer.Developer
import edu.iesam.gametracker.features.setting.domain.developer.GetDeveloperUseCase
import edu.iesam.gametracker.features.setting.domain.resources.GetResourcesUseCase
import edu.iesam.gametracker.features.setting.domain.resources.Resources
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class ResourcesViewmodel(
    private val getResourcesUseCase: GetResourcesUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> = _uiState

    fun loadResources() {
        _uiState.value = UiState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val resources = getResourcesUseCase()
            if (resources.getOrNull() != null) {
                _uiState.postValue(UiState(resources = resources.getOrNull()))
            } else {
                _uiState.postValue(UiState(errorApp = resources.exceptionOrNull() as ErrorApp?))
            }
        }
    }

    data class UiState(
        val isLoading: Boolean = false,
        val errorApp: ErrorApp? = null,
        val resources: List<Resources>? = null
    )
}