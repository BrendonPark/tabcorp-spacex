package me.brendonp.tabcorp.data.entities

data class RocketEntity(
    val rocketId: String?,
    val rocketName: String?,
    val rocketType: String?,
    val description: String?,
    val wikipedia: String?,
    val height: Dimensions?,
    val diameter: Dimensions?,
    val engines: Engines?
) {

    data class Dimensions(
        val meters: Double?,
        val feet: Double?
    )

    data class Core(
        val coreSerial: String?,
        val flight: Int?,
        val bock: Int?,
        val gridFins: Boolean?,
        val legs: Boolean?,
        val reused: Boolean?,
        val landSuccess: Boolean?,
        val landingIntent: Boolean?,
        val landingType: String?,
        val landingVehicle: String?
    )

    data class Engines(
        val number: Int?
    )
}