package me.brendonp.tabcorp.presentation.models

import androidx.annotation.StringRes

data class LaunchDetailsDisplay(
    val missionName: String,
    val rocketName: String?,
    val launchDate: String?,
    @StringRes val launchSuccess: Int,
    val launchSite: String?,
    val bannerImageUrl: String?,
    val missionPatchUrl: String?,
    val launchDescription: String?,
    val rocketDescription: String?,
    val rocketHeight: String?,
    val rocketDiameter: String?,
    val rocketEngines: String?,
    val rocketWikipedia: String?
)