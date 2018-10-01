package com.rpatel.nba.domain.usecase

import com.rpatel.nba.domain.data.NBARepository
import com.rpatel.nba.domain.entity.TeamData.Team
import io.reactivex.Single
import javax.inject.Inject

interface GetTeamsUseCase {
  fun execute(): Single<List<Team>>
}

class GetTeamsUseCaseImpl @Inject constructor(private val repository: NBARepository): GetTeamsUseCase {

  override fun execute(): Single<List<Team>> {
    return repository.fetchTeams()
  }

}