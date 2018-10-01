package com.rpatel.nba.ui.main.fragments

import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.spy
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.rpatel.nba.domain.entity.TeamData
import com.rpatel.nba.domain.usecase.GetTeamsUseCase
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.json.JSONException
import org.junit.After
import org.junit.Test

class TeamsFragmentPresenterTest {

  private lateinit var getTeamsUseCase: GetTeamsUseCase
  private val view: TeamListContract.View = spy(ViewStub())

  private val presenter: TeamsFragmentPresenter by lazy {
    TeamsFragmentPresenter(getTeamsUseCase,
        Schedulers.trampoline(),
        Schedulers.trampoline())
  }

  @After
  fun tearDown() {
    presenter.detachView()
  }

  @Test
  fun `verify that use case returns data and update view successfully`() {

    val teams = listOf(teamRaptors, teamCeltics)

    getTeamsUseCase = GetTeamsUseCaseSuccessStub(teams)
    presenter.attachView(view)
    presenter.loadTeams()
    verify(view, times(1)).updateTeams(teams)
    verify(view, times(1)).showProgress()
    verify(view, times(1)).hideProgress()
  }

  @Test
  fun `verify that use case fails and update view successfully`() {

    val teams = listOf(teamRaptors, teamCeltics)

    getTeamsUseCase = GetTeamsUseCaseFailureStub()
    presenter.attachView(view)
    presenter.loadTeams()
    verify(view, times(0)).updateTeams(teams)
    verify(view, times(1)).showProgress()
    verify(view, times(1)).hideProgress()
  }

  @Test
  fun `verify data sorted successfully`() {
    val teams = listOf(teamRaptors, teamCeltics)
    getTeamsUseCase = GetTeamsUseCaseSuccessStub(teams)
    presenter.attachView(view)

    presenter.loadTeams()
    verify(view, times(1)).updateTeams(teams)

    reset(view)
    presenter.sortBy(0)
    verify(view, times(1)).updateTeams(teams.sortedBy { it.wins })

    reset(view)
    presenter.sortBy(1)
    verify(view, times(1)).updateTeams(teams.sortedByDescending { it.wins })

    reset(view)
    presenter.sortBy(2)
    verify(view, times(1)).updateTeams(teams.sortedBy { it.losses })

    reset(view)
    presenter.sortBy(3)
    verify(view, times(1)).updateTeams(teams.sortedByDescending { it.losses })

    reset(view)
    presenter.sortBy(4)
    verify(view, times(1)).updateTeams(teams.sortedBy { it.fullName })

    reset(view)
    presenter.sortBy(5)
    verify(view, times(1)).updateTeams(teams.sortedByDescending { it.fullName })
  }

  internal class GetTeamsUseCaseSuccessStub(private val teams: List<TeamData.Team>): GetTeamsUseCase {
    override fun execute(): Single<List<TeamData.Team>> {
      return Single.just(teams)
    }
  }

  internal class GetTeamsUseCaseFailureStub: GetTeamsUseCase {
    override fun execute(): Single<List<TeamData.Team>> {
      return Single.error(JSONException("malformed json"))
    }
  }

  internal open class ViewStub: TeamListContract.View {

    override fun updateTeams(teams: List<TeamData.Team>) {}

    override fun showProgress() {}

    override fun hideProgress() {}
  }

  private val raptor39 = TeamData.Player(id = 32123,
      firstName = "Harrison",
      lastName = "Wells",
      position = "PG",
      number = 39)
  private val raptor45 = TeamData.Player(id = 321734,
      firstName = "Cisco",
      lastName = "Ramon",
      position = "SG",
      number = 45)
  private val teamRaptors = TeamData.Team(id = 1,
      fullName = "Toronto Raptors",
      wins = 100,
      losses = 80,
      players = listOf(raptor39, raptor45))

  private val celtic39 = TeamData.Player(id = 37729,
      firstName = "Kadeem",
      lastName = "Allen",
      position = "PG",
      number = 39)
  private val celtic45 = TeamData.Player(id = 37520,
      firstName = "Berry",
      lastName = "Allen",
      position = "SG",
      number = 45)
  private val teamCeltics = TeamData.Team(id = 1,
      fullName = "Boston Celtics",
      wins = 45,
      losses = 20,
      players = listOf(celtic39, celtic45))
}