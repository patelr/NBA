package com.rpatel.nba.ui.main.fragments

import com.rpatel.nba.domain.usecase.GetTeamsUseCase
import dagger.Module
import dagger.Provides

@Module
class TeamsFragmentModule {

  @Provides
  fun provideTeamListFragmentPresenter(getTeamsUseCase: GetTeamsUseCase): TeamsFragmentPresenter {
    return TeamsFragmentPresenter(getTeamsUseCase)
  }

}