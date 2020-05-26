package me.brendonp.tabcorp.data.entities

data class LaunchEntity(
    val flightNumber: String?,
    val missionName: String?,
    val missionId: List<String>?,
    val upcoming: Boolean?,
    val launchYear: String?,
    val launchDateUnix: Long?,
    val launchDateUtc: String?,
    val launchDateLocal: String?,
    val isTentative: Boolean?,
    val tentativeMaxPrecision: String?,
    val tbd: Boolean?,
    val launchWindow: Long?,
    val rocket: RocketEntity?,
    val launchSuccess: Boolean?,
    val launchSite: LaunchSite?,
    val links: Links?,
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
        val redditCampaign: String?,
        val redditLaunch: String?,
        val redditRecovery: String?,
        val redditMedia: String?,
        val pressKit: String?,
        val articleLink: String?,
        val wikipedia: String?,
        val videoLink: String?,
        val youtubeId: String?,
        val flickrImages: List<String>?
    )
}