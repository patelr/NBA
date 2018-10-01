package com.rpatel.nba.domain.usecase

import com.rpatel.nba.domain.data.NBARepository
import com.rpatel.nba.domain.entity.TeamData.Team
import io.reactivex.Single
import org.json.JSONException
import org.junit.Test

class GetTeamsUseCaseTest {

  @Test
  fun `verify that getTeams successfully returns result `() {
    val team1 = Team(1, 12, 9, "Toronto Raptors")
    val team2 = Team(2, 10, 16, "Cleveland Cavaliers")
    val teamList = listOf(team1, team2)

    val repository = NBARepositorySuccessStub(teamList)

    val useCase: GetTeamsUseCase = GetTeamsUseCaseImpl(repository)

    val result = useCase
                  .execute()
                  .test()
                  .assertComplete()
    result.assertValue { it == teamList }
  }

  @Test
  fun `verify that getTeams fails due to JSONException`() {
    val useCase: GetTeamsUseCase = GetTeamsUseCaseImpl(NBARepositoryFailureStub())

    val result = useCase
        .execute()
        .test()
        .assertNotComplete()

    result.assertError { it is JSONException }
  }

  internal class NBARepositorySuccessStub(private val result: List<Team>): NBARepository {

    override fun fetchTeams(): Single<List<Team>> {
      return Single.just(result)
    }

  }

  internal class NBARepositoryFailureStub: NBARepository {

    override fun fetchTeams(): Single<List<Team>> {
      return Single.error(JSONException("malformed json response"))
    }

  }

}