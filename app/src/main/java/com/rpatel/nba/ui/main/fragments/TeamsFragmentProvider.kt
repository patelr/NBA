package com.rpatel.nba.ui.main.fragments

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class TeamsFragmentProvider {

  @ContributesAndroidInjector(modules = [TeamsFragmentModule::class])
  abstract fun provideTeamsFragmentFactory(): TeamsFragment

}