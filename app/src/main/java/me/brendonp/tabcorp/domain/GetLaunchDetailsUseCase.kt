package me.brendonp.tabcorp.domain

import me.brendonp.tabcorp.data.SpaceRepository
import me.brendonp.tabcorp.domain.models.Launch

class GetLaunchDetailsUseCase(
    private val spaceRepo: SpaceRepository
) : UseCase<String, Launch>() {

    override fun execute(parameters: String): Launch {
        val launch = spaceRepo.getLaunch(parameters)!!
        val rocket = spaceRepo.getRocket(launch.rocket!!.id)!!
        return launch.copy(rocket = rocket)
    }
}