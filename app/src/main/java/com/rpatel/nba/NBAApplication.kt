package com.rpatel.nba

import com.rpatel.nba.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication


class NBAApplication: DaggerApplication() {
  override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
    val appComponent = DaggerAppComponent.builder().application(this).build()
    appComponent.inject(this)
    return appComponent
  }
}