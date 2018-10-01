package com.rpatel.nba.ui.adapters.viewholders

import android.support.v7.widget.RecyclerView
import android.view.View
import com.rpatel.nba.domain.entity.DataItem

abstract class BaseViewHolder<T : DataItem>(itemView: View): RecyclerView.ViewHolder(itemView) {

  abstract fun bindView(item: T)
  abstract fun recycleView()
}