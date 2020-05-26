package me.brendonp.tabcorp.domain.models

import java.util.*

data class Launch(
    val flightNumber: String,
    val missionName: String,
    val launchDate: Calendar?,
    val rocket: Rocket?,
    val links: Links,
    val launchSuccess: Boolean?,
    val launchSite: LaunchSite,
    val details: String?
) {

    data class LaunchSite(
        val siteId: String?,
        val siteName: String?,
        val siteNameLong: String?
    )

    data class Links(
        val missionPatch: String?,
        val missionPatchSmall: String?,
        val wikipedia: String?,
        val flickrImages: List<String>
    )
}