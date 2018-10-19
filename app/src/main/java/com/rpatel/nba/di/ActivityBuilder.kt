package com.rpatel.nba.di

import com.rpatel.nba.ui.main.MainActivity
import com.rpatel.nba.ui.main.MainActivityModule
import com.rpatel.nba.ui.main.fragments.TeamsFragmentProvider
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
  @ContributesAndroidInjector(modules = [MainActivityModule::class, TeamsFragmentProvider::class])
  internal abstract fun bindMainActivity(): MainActivity
}