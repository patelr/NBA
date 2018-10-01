package com.rpatel.nba

import com.rpatel.nba.domain.entity.TeamData
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

  @Test
  fun addition_isCorrect() {
    assertEquals(4, 2 + 2)

    val team = TeamData.Team(1, 10, 10, "Full Name")
    val team2 = TeamData.Team(1, 10, 10, "Full Name")

    assertEquals(team, team2)
  }
}
