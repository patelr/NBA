package com.rpatel.nba.domain.provider

import com.rpatel.nba.AllOpen
import com.rpatel.nba.domain.gateway.NBAGateway
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AllOpen

interface NBAGatewayProvider {
  fun provideNBAGateway(): NBAGateway
}

class NBAGatewayProviderImpl @Inject constructor(private val baseUrl: String,
                                                 private val okHttpClient: OkHttpClient): NBAGatewayProvider {

  override fun provideNBAGateway(): NBAGateway {
    return Retrofit
        .Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .build().create(NBAGateway::class.java)
  }
}

sealed class Timeout(val timeoutDuration: Long, val timeoutUnit: TimeUnit) {

  data class MILLISECONDS(val duration: Long): Timeout(duration, TimeUnit.MILLISECONDS)
  data class NONE(val duration: Long = 0): Timeout(duration, TimeUnit.NANOSECONDS)
}



