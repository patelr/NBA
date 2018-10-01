package com.rpatel.nba.di

import com.rpatel.nba.domain.data.NBARepository
import com.rpatel.nba.domain.data.NBARepositoryImpl
import com.rpatel.nba.domain.gateway.NBAGateway
import com.rpatel.nba.domain.provider.NBAGatewayProvider
import com.rpatel.nba.domain.provider.NBAGatewayProviderImpl
import com.rpatel.nba.domain.provider.Timeout
import com.rpatel.nba.domain.usecase.GetTeamsUseCase
import com.rpatel.nba.domain.usecase.GetTeamsUseCaseImpl
import com.rpatel.nba.ui.main.fragments.TeamsFragmentPresenter
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

@Module
class AppModule {

    @Provides
    fun provideTimeout(): Timeout {
      return Timeout.NONE()
    }

    @Provides
    fun provideNBAGatewayProvider(okHttpClient: OkHttpClient)
        : NBAGatewayProvider {
      return NBAGatewayProviderImpl(NBAGateway.BASE_URL, okHttpClient)
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

    @Provides
    fun provideGetTeamsUseCase(nbaRepository: NBARepository): GetTeamsUseCase {
      return GetTeamsUseCaseImpl(nbaRepository)
    }

    @Provides
    fun provideNBARepository(nbaGatewayProvider: NBAGatewayProvider): NBARepository {
      return NBARepositoryImpl(nbaGatewayProvider)
    }
}