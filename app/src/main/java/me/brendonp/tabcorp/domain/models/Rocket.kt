package me.brendonp.tabcorp.domain.models

data class Rocket(
    val id: String,
    val name: String?,
    val height: Double?,
    val diameter: Double?,
    val engineCount: Int?,
    val wikipedia: String?,
    val description: String?
)