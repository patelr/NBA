package com.rpatel.nba.ui.main.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import com.rpatel.nba.R
import com.rpatel.nba.di.AppModule
import com.rpatel.nba.domain.entity.DataItem
import com.rpatel.nba.domain.entity.TeamData.Team
import com.rpatel.nba.ui.adapters.DataAdapter
import com.rpatel.nba.ui.adapters.viewholders.BaseViewHolder
import com.rpatel.nba.ui.adapters.viewholders.TeamViewHolder
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.item_list.*
import javax.inject.Inject

class TeamsFragment: Fragment(), TeamListContract.View, HasSupportFragmentInjector {

  @Inject
  lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>

  @Inject
  lateinit var presenter: TeamsFragmentPresenter

  private var currentSort: Int = 4

  private var teamsList: List<Team> = emptyList()

  private val adapter: DataAdapter by lazy {
    object: DataAdapter() {
      override fun provideViewHolder(dateItemType: Int, parent: ViewGroup): BaseViewHolder<DataItem> {
        return TeamViewHolder.newInstance(parent) as BaseViewHolder<DataItem>
      }
    }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    setHasOptionsMenu(true)
    return inflater.inflate(R.layout.item_list, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    teams_list.adapter = adapter
    presenter.attachView(this)
    presenter.loadTeams()
  }

  override fun onDestroyView() {
    super.onDestroyView()
    presenter.detachView()
  }

  override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
    inflater?.inflate(R.menu.teams_menu, menu)
    super.onCreateOptionsMenu(menu, inflater)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    if(item?.itemId == R.id.sort) {
      showSortOptions()
      return true
    }
    return super.onOptionsItemSelected(item)
  }

  override fun showProgress() {
    Log.d("TAG", "showProgress")
  }

  override fun hideProgress() {
    Log.d("TAG", "hideProgress")
  }

  override fun updateTeams(teams: List<Team>) {
    teamsList = teams
    adapter.updateData(teams)
  }

  private fun showSortOptions() {
    val array = context?.resources?.getStringArray(R.array.sort_option)

    AlertDialog
        .Builder(context)
        .setTitle(R.string.sort_by)
        .setSingleChoiceItems(array, currentSort) { dialog, which ->
          currentSort = if( which == currentSort) -1 else which
          presenter.sortBy(currentSort)
          dialog.dismiss()
        }
        .setNegativeButton(android.R.string.cancel) {dialog, _ ->
          dialog.dismiss()
        }
        .create()
        .show()
  }

  override fun onAttach(context: Context?) {
    AndroidSupportInjection.inject(this)
    super.onAttach(context)
  }

  override fun supportFragmentInjector(): AndroidInjector<Fragment> {
    return childFragmentInjector
  }
}