package me.brendonp.tabcorp.application

import me.brendonp.tabcorp.data.SpaceRepository
import me.brendonp.tabcorp.data.SpaceXService
import me.brendonp.tabcorp.domain.GetLaunchDetailsUseCase
import me.brendonp.tabcorp.domain.GetLaunchListUseCase

/**
 * A simple dependency injection container for the space demo
 * Since the project scope is small we can use an approach like this.
 * During a larger project we would use a DI framework like Dagger, as this isn't easily maintainable.
 */
object DependencyContainer {
    private var isInitialized = false

    private val spaceRepository: SpaceRepository by lazy {
        SpaceXService()
    }

    fun initialize() {
        check(!isInitialized) { "The dependency container has already been initialized" }
        isInitialized = true
    }

    fun provideLaunchListUseCase(): GetLaunchListUseCase {
        return GetLaunchListUseCase(spaceRepository)
    }

    fun provideLaunchDetailsUseCase(): GetLaunchDetailsUseCase {
        return GetLaunchDetailsUseCase(spaceRepository)
    }
}