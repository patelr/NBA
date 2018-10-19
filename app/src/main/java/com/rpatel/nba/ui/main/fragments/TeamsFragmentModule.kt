package com.rpatel.nba.ui.main.fragments

import com.rpatel.nba.domain.usecase.GetTeamsUseCase
import com.rpatel.nba.domain.usecase.GetTeamsUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
abstract class TeamsFragmentModule {

  @Binds
  abstract fun getTeamsUseCase(getTeamsUseCase: GetTeamsUseCaseImpl): GetTeamsUseCase
}