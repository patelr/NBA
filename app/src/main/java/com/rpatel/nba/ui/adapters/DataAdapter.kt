package com.rpatel.nba.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.rpatel.nba.domain.entity.DataItem
import com.rpatel.nba.ui.adapters.viewholders.BaseViewHolder

abstract class DataAdapter: RecyclerView.Adapter<BaseViewHolder<DataItem>> (){

  abstract fun provideViewHolder(dateItemType: Int, parent: ViewGroup): BaseViewHolder<DataItem>

  init {
    this.setHasStableIds(true)
  }

  private var data: List<DataItem> = emptyList()

  override fun getItemViewType(position: Int): Int {
    return data[position].getDataItemType()
  }

  override fun onCreateViewHolder(parent: ViewGroup, type: Int): BaseViewHolder<DataItem> {
    return provideViewHolder(type, parent)
  }

  override fun getItemCount(): Int {
    return data.size
  }

  override fun onBindViewHolder(holder: BaseViewHolder<DataItem>, position: Int) {
    holder.bindView(data[position])
  }

  override fun getItemId(position: Int): Long {
    return data[position].getStableId()
  }

  override fun onViewRecycled(holder: BaseViewHolder<DataItem>) {
    super.onViewRecycled(holder)
    holder.recycleView()
  }

  fun updateData(newData: List<DataItem>){
    data = newData
    notifyDataSetChanged()
  }
}



