package me.brendonp.tabcorp.presentation

import me.brendonp.tabcorp.R
import me.brendonp.tabcorp.domain.models.Launch
import me.brendonp.tabcorp.presentation.models.LaunchDetailsDisplay
import me.brendonp.tabcorp.presentation.models.LaunchDisplayItemList
import java.text.SimpleDateFormat
import java.util.*

/**
 * Provides standardized mapping from the domain level objects to display objects.
 * This allows for the same presentation rules like date formats to be used across multiple viewmodels
 * easily.
 */
object DisplayMapper {

    private val DISPLAY_DATE_FORMAT = SimpleDateFormat("d MMMM, yyyy", Locale.ENGLISH)

    fun mapToLaunchDisplayItem(launch: Launch): LaunchDisplayItemList {
        return LaunchDisplayItemList(
            id = launch.flightNumber,
            missionName = launch.missionName,
            launchDate = formatDate(launch.launchDate),
            rocketName = launch.rocket?.name,
            backingData = launch
        )
    }

    fun mapToLaunchDetailsDisplay(launch: Launch): LaunchDetailsDisplay {
        val launchSuccess = when (launch.launchSuccess) {
            true -> R.string.launch_successful
            false -> R.string.launch_unsuccessful
            else -> R.string.launch_success_unknown
        }

        return LaunchDetailsDisplay(
            missionName = launch.missionName,
            rocketName = launch.rocket?.name,
            launchDate = formatDate(launch.launchDate) ?: "-",
            launchSuccess = launchSuccess,
            bannerImageUrl = launch.links.flickrImages.firstOrNull(),
            missionPatchUrl = launch.links.missionPatchSmall,
            launchDescription = launch.details,
            launchSite = launch.launchSite.siteNameLong,
            rocketDescription = launch.rocket?.description,
            rocketHeight = launch.rocket?.height?.toString(),
            rocketDiameter = launch.rocket?.diameter?.toString(),
            rocketEngines = launch.rocket?.engineCount?.toString(),
            rocketWikipedia = launch.rocket?.wikipedia
        )
    }

    private fun formatDate(date: Calendar?): String? {
        return date?.let {
            DISPLAY_DATE_FORMAT.format(it.time)
        }
    }
}