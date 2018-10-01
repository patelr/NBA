package com.rpatel.nba.ui.adapters.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.rpatel.nba.R
import com.rpatel.nba.domain.entity.TeamData
import com.rpatel.nba.ui.detail.DetailActivity

class TeamViewHolder(itemView: View): BaseViewHolder<TeamData.Team>(itemView){

  private val teamName = itemView.findViewById<TextView>(R.id.teamName)
  private val teamWins = itemView.findViewById<TextView>(R.id.wins)
  private val teamLosses = itemView.findViewById<TextView>(R.id.losses)

  init {
    itemView.setOnClickListener {
      DetailActivity.startActivity(it.context, it.tag as TeamData.Team)
    }
  }

  override fun bindView(item: TeamData.Team) {
    teamName.text = item.fullName
    teamWins.text = item.wins.toString()
    teamLosses.text = item.losses.toString()
    itemView.tag = item
  }

  override fun recycleView() { }

  companion object {
    fun newInstance(parent: ViewGroup): BaseViewHolder<TeamData.Team> {
      return TeamViewHolder(
          LayoutInflater.from(parent.context).inflate(R.layout.team_list_item, parent, false)
      )
    }
  }
}