package com.rpatel.nba.domain.data

import com.google.gson.Gson
import com.rpatel.nba.di.AppModule
import com.rpatel.nba.domain.entity.TeamData.Player
import com.rpatel.nba.domain.entity.TeamData.Team
import com.rpatel.nba.domain.provider.NBAGatewayProvider
import com.rpatel.nba.domain.provider.NBAGatewayProviderImpl
import com.rpatel.nba.domain.provider.Timeout
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.net.SocketTimeoutException

class NBARepositoryTest {

  private val appModule: AppModule by lazy {
    AppModule()
  }

  private var timeout: Timeout = Timeout.NONE()
  private val baseUrl: String by lazy {
    "http://${mockWebServer.hostName}:${mockWebServer.port}"
  }
  private val mockWebServer: MockWebServer by lazy {
    MockWebServer()
  }

  private val okHttpClient: OkHttpClient by lazy {
    appModule.provideOkHttpClient(timeout)
  }

  private val gateway: NBAGatewayProvider by lazy {
    NBAGatewayProviderImpl(baseUrl, okHttpClient)
  }

  @Before
  fun setup() {
    mockWebServer.start()
  }

  @After
  fun tearDown() {
    mockWebServer.shutdown()
  }

  @Test
  fun `verify server returns valid response`() {
    mockWebServer.enqueue(MockResponse().apply {
      this.setResponseCode(200)
      this.setBody(mockResponseBody(listOf(teamRaptors, teamCeltics)))
    })

    val repository = NBARepositoryImpl(gateway)
    val result = repository
                  .fetchTeams()
                  .retry(3)
                  .test()
                  .assertComplete()

    result.assertValue { it.isNotEmpty() }
    assertEquals( teamRaptors, result.values().first().first())
    assertEquals( teamCeltics, result.values().first().last())
    assertEquals(1, mockWebServer.requestCount)
  }

  @Test
  fun `verify retry return success on failed attempts`() {
    val firstResponse = MockResponse().apply {
      this.setResponseCode(503)
    }

    val lastResponse = MockResponse().apply {
      this.setResponseCode(200)
      this.setBody(mockResponseBody(listOf(teamRaptors, teamCeltics)))
    }

    mockWebServer.enqueue(firstResponse)
    mockWebServer.enqueue(firstResponse)
    mockWebServer.enqueue(firstResponse)
    mockWebServer.enqueue(lastResponse)

    val repository = NBARepositoryImpl(gateway)
    val result = repository
        .fetchTeams()
        .retry(3)
        .test()
        .assertComplete()

    result.assertValue { it.isNotEmpty() }
    assertEquals( teamRaptors, result.values().first().first())
    assertEquals( teamCeltics, result.values().first().last())
    assertEquals(4, mockWebServer.requestCount)
  }

  @Test
  fun `verify request timeout`() {
    timeout = Timeout.MILLISECONDS(20)

    val repository = NBARepositoryImpl(gateway)
    val result = repository
        .fetchTeams()
        .retry(3)
        .test()
        .assertNotComplete()

    result.assertError { it is SocketTimeoutException }
    assertEquals(4, mockWebServer.requestCount)
  }

  private val raptor39 = Player(id = 32123,
      firstName = "Harrison",
      lastName = "Wells",
      position = "PG",
      number = 39)
  private val raptor45 = Player(id = 321734,
      firstName = "Cisco",
      lastName = "Ramon",
      position = "SG",
      number = 45)
  private val teamRaptors = Team(id = 1,
      fullName = "Toronto Raptors",
      wins = 100,
      losses = 80,
      players = listOf(raptor39, raptor45))

  private val celtic39 = Player(id = 37729,
      firstName = "Kadeem",
      lastName = "Allen",
      position = "PG",
      number = 39)
  private val celtic45 = Player(id = 37520,
      firstName = "Berry",
      lastName = "Allen",
      position = "SG",
      number = 45)
  private val teamCeltics = Team(id = 1,
      fullName = "Boston Celtics",
      wins = 45,
      losses = 20,
      players = listOf(celtic39, celtic45))

  private fun mockResponseBody(teams: List<Team> = emptyList()): String {
    return Gson().toJson(teams)
  }
}