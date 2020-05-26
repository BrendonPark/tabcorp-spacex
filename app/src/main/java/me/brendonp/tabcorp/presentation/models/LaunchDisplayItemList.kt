package me.brendonp.tabcorp.presentation.models

import androidx.annotation.StringRes
import me.brendonp.tabcorp.domain.models.Launch

data class LaunchDisplayItemList(
    override val id: String,
    val missionName: String,
    val launchDate: String?,
    val rocketName: String?,
    val backingData: Launch
) : BaseDisplayItem