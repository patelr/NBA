package com.rpatel.nba.ui.main.fragments

import com.rpatel.nba.AllOpen
import com.rpatel.nba.domain.entity.TeamData.Team
import com.rpatel.nba.domain.usecase.GetTeamsUseCase
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Named

@AllOpen

interface TeamListContract {

  interface View{
    fun showProgress()
    fun hideProgress()
    fun updateTeams(teams: List<Team>)
    fun showError(error: Throwable)
  }

  interface Presenter {
    fun loadTeams()
    fun attachView(view: View)
    fun detachView()
    fun sortBy(type: Int)
  }
}

class TeamsFragmentPresenter @Inject constructor(private val getTeamsUseCase: GetTeamsUseCase,
                                                 @Named("scheduler") private val scheduler: Scheduler = Schedulers.io(),
                                                 @Named("mainScheduler") private val mainScheduler: Scheduler = AndroidSchedulers.mainThread())
  : TeamListContract.Presenter {

  private val compositeDisposable = CompositeDisposable()
  private lateinit var view: TeamListContract.View
  private var teams: List<Team> = emptyList()

  override fun loadTeams() {
    getTeamsUseCase
        .execute()
        .subscribeOn(scheduler)
        .observeOn(mainScheduler)
        .doOnSubscribe {
          view.showProgress()
        }
        .subscribe { teams, e ->
          view.hideProgress()
          e?.let {
            view.showError(it)
            return@subscribe
          }
          this.teams = teams
          sortBy(4)
        }.also {
          compositeDisposable.add(it)
        }
  }

  override fun attachView(view: TeamListContract.View) {
    this.view = view
  }

  override fun detachView() {
    compositeDisposable.dispose()
  }

  override fun sortBy(type: Int) {
    if(type == -1) {
      view.updateTeams(teams)
      return
    }
    Single.create<List<Team>> {
      it.onSuccess(
          when(type) {
            0 -> { teams.sortedBy { team -> team.wins } }
            1 -> { teams.sortedByDescending { team -> team.wins } }
            2 -> { teams.sortedBy { team -> team.losses } }
            3 -> { teams.sortedByDescending { team -> team.losses } }
            4 -> { teams.sortedBy { team -> team.fullName } }
            5 -> { teams.sortedByDescending { team -> team.fullName } }
            else -> {
              teams
            }
          })
    }.subscribeOn(scheduler)
    .observeOn(mainScheduler)
    .doOnSubscribe { view.showProgress() }
    .subscribe { sortedTeams, _ ->
      view.updateTeams(sortedTeams)
      view.hideProgress()
    }.also {
      compositeDisposable.add(it)
    }
  }
}