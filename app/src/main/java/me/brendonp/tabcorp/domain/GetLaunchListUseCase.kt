package me.brendonp.tabcorp.domain

import me.brendonp.tabcorp.data.SpaceRepository
import me.brendonp.tabcorp.domain.models.Launch

class GetLaunchListUseCase(
    private val spaceRepo: SpaceRepository
) : UseCase<Unit, List<Launch>>() {

    override fun execute(parameters: Unit): List<Launch> {
        return spaceRepo.getLaunches()
    }
}