package com.rpatel.nba.ui.main


import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import android.view.ViewGroup
import com.rpatel.nba.R
import com.rpatel.nba.ui.detail.DetailActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.content.Intent
import android.support.test.InstrumentationRegistry
import com.rpatel.nba.domain.entity.TeamData


@LargeTest
@RunWith(AndroidJUnit4::class)
class DetailActivityTest {

  @JvmField
  @Rule
  var mActivityRule: ActivityTestRule<DetailActivity> = object : ActivityTestRule<DetailActivity>(DetailActivity::class.java) {
    override fun getActivityIntent(): Intent {
      val targetContext = InstrumentationRegistry.getInstrumentation()
          .targetContext
      val result = Intent(targetContext, DetailActivity::class.java)
      result.putExtra("extra_team_data_key", team)
      return result
    }
  }

  @Test
  fun detailActivityTest() {
    val textView = onView(
        allOf(withId(R.id.playerName), withText("Kadeem All"),
            childAtPosition(
                childAtPosition(
                    IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java),
                    0),
                1),
            isDisplayed()))
    textView.check(matches(withText("Kadeem All")))
  }


  @Test
  fun checkActivityTitle() {
    val textView = onView(
        allOf(withText(team.fullName),
            childAtPosition(
                allOf(withId(R.id.detail_toolbar),
                    childAtPosition(
                        withId(R.id.app_bar),
                        0)),
                0),
            isDisplayed()))
    textView.check(matches(withText(team.fullName)))
  }

  private fun childAtPosition(
      parentMatcher: Matcher<View>, position: Int): Matcher<View> {

    return object : TypeSafeMatcher<View>() {
      override fun describeTo(description: Description) {
        description.appendText("Child at position $position in parent ")
        parentMatcher.describeTo(description)
      }

      public override fun matchesSafely(view: View): Boolean {
        val parent = view.parent
        return parent is ViewGroup && parentMatcher.matches(parent)
            && view == parent.getChildAt(position)
      }
    }
  }

  private val player1 = TeamData.Player(id = 1, number = 10, position = "PG", firstName = "Kadeem", lastName = "All")
  private val team  = TeamData.Team(id = 1, wins = 100, losses = 50, fullName = "Toronto Raptors", players = listOf(player1))
}
