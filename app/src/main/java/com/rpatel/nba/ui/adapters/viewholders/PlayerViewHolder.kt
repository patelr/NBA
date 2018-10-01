package com.rpatel.nba.ui.adapters.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.rpatel.nba.R
import com.rpatel.nba.domain.entity.TeamData

class PlayerViewHolder(itemView: View): BaseViewHolder<TeamData.Player>(itemView){

  private val playerName = itemView.findViewById<TextView>(R.id.playerName)
  private val playerNumber = itemView.findViewById<TextView>(R.id.playerNumber)
  private val playerPosition = itemView.findViewById<TextView>(R.id.playerPosition)

  override fun bindView(item: TeamData.Player) {
    playerName.text = String.format("%s %s", item.firstName, item.lastName)
    playerNumber.text = item.number.toString()
    playerPosition.text = item.position
    itemView.tag = item
  }

  override fun recycleView() { }

  companion object {
    fun newInstance(parent: ViewGroup): BaseViewHolder<TeamData.Player> {
      return PlayerViewHolder(
          LayoutInflater.from(parent.context).inflate(R.layout.player_list_item, parent, false)
      )
    }
  }
}