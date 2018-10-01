package com.rpatel.nba.domain.data

import com.rpatel.nba.AllOpen
import com.rpatel.nba.domain.entity.TeamData.Team
import com.rpatel.nba.domain.gateway.NBAGateway
import com.rpatel.nba.domain.provider.NBAGatewayProvider
import io.reactivex.Single
import javax.inject.Inject

@AllOpen

interface NBARepository {

  fun fetchTeams(): Single<List<Team>>
}

class NBARepositoryImpl @Inject constructor(val gatewayProvider: NBAGatewayProvider): NBARepository {

  val gateway: NBAGateway by lazy { gatewayProvider.provideNBAGateway() }

  override fun fetchTeams(): Single<List<Team>> {
    return gateway.getTeams()
  }
}