package me.brendonp.tabcorp.presentation.details

import androidx.lifecycle.*
import me.brendonp.tabcorp.R
import me.brendonp.tabcorp.application.DependencyContainer
import me.brendonp.tabcorp.domain.models.Launch
import me.brendonp.tabcorp.presentation.DisplayMapper
import me.brendonp.tabcorp.presentation.models.LaunchDetailsDisplay

class LaunchDetailsViewModel : ViewModel() {

    private val getLaunchDetailsUseCase = DependencyContainer.provideLaunchDetailsUseCase()
    private val backingLaunchData = MediatorLiveData<Result<Launch>>()
    private var launchId: String? = null

    val displayLoading = MutableLiveData<Boolean>()
    val displayError = MutableLiveData<Boolean>()

    val displayDetails = backingLaunchData.map { result ->
        displayError.postValue(result.isFailure)
        displayLoading.postValue(false)

        result.getOrNull()?.let {
            DisplayMapper.mapToLaunchDetailsDisplay(it)
        }
    }

    fun retry() {
        launchId?.let {
            loadLaunchDetails(it)
        }
    }

    fun loadLaunchDetails(launchId: String) {
        this.launchId = launchId
        displayError.postValue(false)
        displayLoading.postValue(true)

        // Swap the backing LiveData with a fresh version from the invoked UseCase
        getLaunchDetailsUseCase.cachedResult?.let {
            backingLaunchData.removeSource(it)
        }
        backingLaunchData.addSource(getLaunchDetailsUseCase(launchId)) {
            backingLaunchData.postValue(it)
        }
    }
}