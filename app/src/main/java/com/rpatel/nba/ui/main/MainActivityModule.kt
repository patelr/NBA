package com.rpatel.nba.ui.main

import com.rpatel.nba.domain.data.NBARepository
import com.rpatel.nba.domain.data.NBARepositoryImpl
import com.rpatel.nba.domain.provider.NBAGatewayProvider
import com.rpatel.nba.domain.provider.NBAGatewayProviderImpl
import dagger.Binds
import dagger.Module

@Module
abstract class MainActivityModule {

  @Binds
  abstract fun nbaRepository(nbaRepositoryImpl: NBARepositoryImpl): NBARepository

  @Binds
  abstract fun nbaGatewayProvider(nbaGatewayProvider: NBAGatewayProviderImpl): NBAGatewayProvider
}