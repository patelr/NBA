package com.rpatel.nba.di

import com.rpatel.nba.domain.gateway.NBAGateway
import com.rpatel.nba.domain.provider.Timeout
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import javax.inject.Named

@Module
class AppModule {

    @Provides
    @Named("scheduler")
    fun provideScheduler(): Scheduler {
      return Schedulers.io()
    }

    @Provides
    @Named("mainScheduler")
    fun provideMainScheduler(): Scheduler {
      return AndroidSchedulers.mainThread()
    }

    @Provides
    @Named("nba_base_url")
    fun provideNBABaseUrl(): String {
      return NBAGateway.BASE_URL
    }

    @Provides
    fun provideTimeout(): Timeout {
      return Timeout.NONE()
    }

    @Provides
    fun provideOkHttpClient(timeout: Timeout): OkHttpClient {
      val okHttpBuilder = OkHttpClient.Builder()
      if(timeout != Timeout.NONE()) {
        okHttpBuilder.connectTimeout(timeout.timeoutDuration, timeout.timeoutUnit)
        okHttpBuilder.readTimeout(timeout.timeoutDuration, timeout.timeoutUnit)
        okHttpBuilder.writeTimeout(timeout.timeoutDuration, timeout.timeoutUnit)
      }
      return okHttpBuilder.build()
    }
}