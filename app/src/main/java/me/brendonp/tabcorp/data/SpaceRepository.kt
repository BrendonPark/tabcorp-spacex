package me.brendonp.tabcorp.data

import me.brendonp.tabcorp.domain.models.Launch
import me.brendonp.tabcorp.domain.models.Rocket

interface SpaceRepository {

    fun getLaunches(): List<Launch>

    fun getLaunch(id: String): Launch?

    fun getRocket(id: String): Rocket?
}