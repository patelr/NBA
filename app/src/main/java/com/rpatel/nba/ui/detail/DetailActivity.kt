package com.rpatel.nba.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import com.rpatel.nba.R
import com.rpatel.nba.domain.entity.DataItem
import com.rpatel.nba.domain.entity.TeamData
import com.rpatel.nba.ui.adapters.DataAdapter
import com.rpatel.nba.ui.adapters.viewholders.BaseViewHolder
import com.rpatel.nba.ui.adapters.viewholders.PlayerViewHolder
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_list.*

class DetailActivity : AppCompatActivity() {

  companion object {
    private const val KEY_EXTRA_TEAM = "extra_team_data_key"

    fun startActivity(context: Context, team: TeamData.Team) {
      val intent = Intent(context, DetailActivity::class.java)
      intent.putExtra(KEY_EXTRA_TEAM, team)
      context.startActivity(intent)
    }
  }

  @Suppress("UNCHECKED_CAST")
  private val adapter: DataAdapter by lazy {
    object: DataAdapter() {
      override fun provideViewHolder(dateItemType: Int, parent: ViewGroup)
          : BaseViewHolder<DataItem> {
        return PlayerViewHolder.newInstance(parent) as BaseViewHolder<DataItem>
      }
    }
  }

  private val team: TeamData.Team by lazy {
    intent.getParcelableExtra<TeamData.Team>(KEY_EXTRA_TEAM)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_detail)
    detail_toolbar.title = team.fullName
    detail_toolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back)
    detail_toolbar.setNavigationOnClickListener { finish() }
    setSupportActionBar(toolbar)

    wins.text = String.format("Wins: %d", team.wins)
    losses.text = String.format("Losses: %d", team.losses)
    teams_list.adapter = adapter
    adapter.updateData(team.players)
  }
}
