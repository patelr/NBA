package com.rpatel.nba.domain.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

interface DataItem {
  fun getStableId(): Long
  fun getDataItemType(): Int
}

sealed class TeamData(val itemType: Int): DataItem {

  override fun getDataItemType(): Int {
    return itemType
  }

  @Parcelize
  data class Team(
      val id: Int,
      val wins: Int,
      val losses: Int,
      @SerializedName("full_name") val fullName: String,
      val players: @RawValue List<Player> = emptyList())
    : TeamData(TYPE), Parcelable {

    override fun getStableId(): Long {
      return (itemType.toLong() * Int.MAX_VALUE) + hashCode().toLong()
    }

    companion object {
      const val TYPE = 1001
    }
  }

  @Parcelize
  data class Player (
      val id: Int,
      @SerializedName("first_name") val firstName: String = "",
      @SerializedName("last_name") val lastName: String = "",
      val position: String = "",
      val number: Int)
    : TeamData(TYPE), Parcelable {

    override fun getStableId(): Long {
      return (itemType.toLong() * Int.MAX_VALUE) + hashCode().toLong()
    }

    companion object {
       const val TYPE = 1002
    }

  }

}

