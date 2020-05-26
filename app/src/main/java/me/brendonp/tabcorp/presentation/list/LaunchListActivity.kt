package me.brendonp.tabcorp.presentation.list

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.iterator
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_launch_list.*
import kotlinx.android.synthetic.main.item_loading_container.*
import me.brendonp.tabcorp.R
import me.brendonp.tabcorp.presentation.details.LaunchDetailsActivity
import me.brendonp.tabcorp.presentation.models.BaseDisplayItem
import me.brendonp.tabcorp.presentation.models.LaunchDisplayItemList

class LaunchListActivity : AppCompatActivity() {

    private val viewModel: LaunchListViewModel by viewModels()
    private lateinit var adapter: LaunchListAdapter
    private lateinit var menu: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch_list)

        viewModel.launchDisplayItems.observe(this, ::displayItems)
        viewModel.displayLoading.observe(this, ::displayLoading)
        viewModel.displayError.observe(this, ::displayError)

        adapter = LaunchListAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        retryButton.setOnClickListener { viewModel.onRetry() }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.launch_list, menu)
        this.menu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.filter_all -> viewModel.onFilterAll()
            R.id.filter_successful -> viewModel.onFilterSuccessful()
            R.id.filter_unsuccessful -> viewModel.onFilterUnsuccessful()
            R.id.sort_launch_date -> viewModel.onSortByDate()
            R.id.sort_mission_name -> viewModel.onSortByMissionName()
            else -> return super.onOptionsItemSelected(item)
        }

        // Uncheck all items in the group, and check the selected one
        menu.iterator().forEach {
            if (it.groupId == item.groupId && it.itemId != item.itemId) {
                it.isChecked = false
            }
        }
        item.isChecked = true

        return true
    }

    private fun displayLoading(loading: Boolean) {
        if (loading) {
            progressBar.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    private fun displayError(error: Boolean) {
        if (error) {
            errorText.visibility = View.VISIBLE
            retryButton.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            errorText.visibility = View.GONE
            retryButton.visibility = View.GONE
        }
    }

    private fun displayItems(items: List<BaseDisplayItem>?) {
        if (items?.isNotEmpty() == true) {
            recyclerView.visibility = View.VISIBLE
        }
        adapter.setData(items ?: emptyList(), ::onLaunchClick)
    }

    private fun onLaunchClick(launchDisplayItem: LaunchDisplayItemList) {
        val intent = LaunchDetailsActivity.makeIntent(launchDisplayItem.id, this)
        startActivity(intent)
    }
}