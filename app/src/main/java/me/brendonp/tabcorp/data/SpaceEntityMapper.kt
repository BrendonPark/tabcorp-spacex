package me.brendonp.tabcorp.data

import me.brendonp.tabcorp.data.entities.LaunchEntity
import me.brendonp.tabcorp.data.entities.RocketEntity
import me.brendonp.tabcorp.domain.models.Launch
import me.brendonp.tabcorp.domain.models.Rocket
import java.text.SimpleDateFormat
import java.util.*

/**
 * Provides standardized mapping from entities to domain models.
 */
object SpaceEntityMapper {
    private val DATE_FORMAT: SimpleDateFormat =
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)

    @Throws(NullPointerException::class)
    fun mapLaunch(entity: LaunchEntity): Launch {
        return entity.run {
            Launch(
                flightNumber = flightNumber!!,
                missionName = missionName!!,
                launchDate = parseDate(launchDateLocal),
                rocket = mapRocket(rocket),
                links = Launch.Links(
                    missionPatch = links?.missionPatch,
                    missionPatchSmall = links?.missionPatchSmall,
                    wikipedia = links?.wikipedia,
                    flickrImages = links?.flickrImages ?: emptyList()
                ),
                launchSite = Launch.LaunchSite(
                    siteId = launchSite?.siteId,
                    siteName = launchSite?.siteName,
                    siteNameLong = launchSite?.siteNameLong
                ),
                details = details,
                launchSuccess = launchSuccess
            )
        }
    }

    @Throws(NullPointerException::class)
    fun mapRocket(entity: RocketEntity?): Rocket? {
        return entity?.run {
            Rocket(
                id = rocketId!!,
                name = rocketName,
                height = height?.meters,
                diameter = diameter?.meters,
                engineCount = engines?.number,
                wikipedia = wikipedia,
                description = description
            )
        }
    }

    private fun parseDate(date: String?): Calendar? {
        return date?.let {
            val calendar = Calendar.getInstance()
            val parsed = DATE_FORMAT.parse(it)!!
            calendar.time = parsed
            calendar
        }
    }
}