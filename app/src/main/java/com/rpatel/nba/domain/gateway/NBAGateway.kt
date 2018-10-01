package com.rpatel.nba.domain.gateway

import com.rpatel.nba.domain.entity.TeamData.Team
import io.reactivex.Single
import retrofit2.http.GET

interface NBAGateway {

  @GET("master/input.json")
  fun getTeams(): Single<List<Team>>

  companion object {

    const val BASE_URL = "https://raw.githubusercontent.com/scoremedia/nba-team-viewer/"
  }

}