package me.brendonp.tabcorp.presentation.list

import androidx.lifecycle.*
import me.brendonp.tabcorp.application.DependencyContainer
import me.brendonp.tabcorp.domain.models.Launch
import me.brendonp.tabcorp.presentation.DisplayMapper
import me.brendonp.tabcorp.presentation.models.BaseDisplayItem
import me.brendonp.tabcorp.presentation.models.HeaderDisplayItem
import java.util.*

class LaunchListViewModel : ViewModel() {

    private companion object {
        val DEFAULT_SORT = Sort.LAUNCH_DATE
        val DEFAULT_FILTER = Filter.ALL
    }

    private val getLaunchListUseCase = DependencyContainer.provideLaunchListUseCase()
    private val sortAndFilterSelection = MutableLiveData(
        FilterSortSelection(
            DEFAULT_FILTER,
            DEFAULT_SORT
        )
    )
    private val backingLaunchData = MediatorLiveData<Result<List<Launch>?>>()

    val displayLoading = MutableLiveData<Boolean>()
    val displayError = MutableLiveData<Boolean>()

    val launchDisplayItems = sortAndFilterSelection.switchMap { filterSortSelection ->
        backingLaunchData.map {
            displayError.postValue(it.isFailure)
            displayLoading.postValue(false)

            val launches = it.getOrNull()
            val filtered = filter(filterSortSelection.filter, launches)
            val sorted = sort(filterSortSelection.sort, filtered)
            group(filterSortSelection.sort, sorted)
        }
    }

    init {
        getLaunches()
    }

    fun onRetry() = getLaunches()

    fun onSortByDate() = setSort(Sort.LAUNCH_DATE)

    fun onSortByMissionName() = setSort(Sort.MISSION_NAME)

    fun onFilterAll() = setFilter(Filter.ALL)

    fun onFilterSuccessful() = setFilter(Filter.SUCCESSFUL)

    fun onFilterUnsuccessful() = setFilter(Filter.UNSUCCESSFUL)

    private fun getLaunches() {
        displayError.postValue(false)
        displayLoading.postValue(true)

        // Swap the backing LiveData with a fresh version from the invoked UseCase
        getLaunchListUseCase.cachedResult?.let {
            backingLaunchData.removeSource(it)
        }
        backingLaunchData.addSource(getLaunchListUseCase(Unit)) {
            backingLaunchData.postValue(it)
        }
    }

    private fun setSort(sort: Sort) {
        sortAndFilterSelection.postValue(
            FilterSortSelection(
                sortAndFilterSelection.value?.filter ?: DEFAULT_FILTER,
                sort
            )
        )
    }

    private fun setFilter(filter: Filter) {
        sortAndFilterSelection.postValue(
            FilterSortSelection(
                filter,
                sortAndFilterSelection.value?.sort ?: DEFAULT_SORT
            )
        )
    }

    private fun sort(sort: Sort, launches: List<Launch>?): List<Launch>? {
        return when (sort) {
            Sort.MISSION_NAME -> launches?.sortedBy { it.missionName }
            Sort.LAUNCH_DATE -> launches?.sortedByDescending { it.launchDate?.timeInMillis }
        }
    }

    private fun filter(filter: Filter, launches: List<Launch>?): List<Launch>? {
        return when (filter) {
            Filter.ALL -> launches
            Filter.SUCCESSFUL -> launches?.filter { it.launchSuccess == true }
            Filter.UNSUCCESSFUL -> launches?.filter { it.launchSuccess == false }
        }
    }

    private fun group(sort: Sort, launches: List<Launch>?): List<BaseDisplayItem>? {
        val groupMap = launches?.groupBy {
            // Provide default groupings for launches without a mission name or launch date
            when (sort) {
                Sort.LAUNCH_DATE -> it.launchDate?.get(Calendar.YEAR)?.toString() ?: "-"
                Sort.MISSION_NAME -> it.missionName.firstOrNull()?.toString() ?: "-"
            }
        }

        val displayItems = mutableListOf<BaseDisplayItem>()
        groupMap?.keys?.forEach { key ->
            displayItems.add(HeaderDisplayItem(id = key, title = key))
            groupMap[key]?.mapTo(displayItems, DisplayMapper::mapToLaunchDisplayItem)
        }

        return displayItems
    }

    private data class FilterSortSelection(
        val filter: Filter,
        val sort: Sort
    )

    private enum class Filter {
        ALL,
        SUCCESSFUL,
        UNSUCCESSFUL
    }

    private enum class Sort {
        LAUNCH_DATE,
        MISSION_NAME
    }
}