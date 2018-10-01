package com.rpatel.nba.di

import com.rpatel.nba.NBAApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import javax.inject.Singleton


@Singleton
@Component (modules = [ AndroidSupportInjectionModule::class,
                        AppModule::class,
                        ActivityBuilder::class])
interface AppComponent: AndroidInjector<DaggerApplication> {

  override fun inject(instance: DaggerApplication)

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun application(application: NBAApplication): Builder

    fun build(): AppComponent
  }

  fun inject(app: NBAApplication)
}

