package me.brendonp.tabcorp.presentation.details

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_launch_details.*
import kotlinx.android.synthetic.main.item_loading_container.*
import me.brendonp.tabcorp.R
import me.brendonp.tabcorp.presentation.models.LaunchDetailsDisplay

class LaunchDetailsActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_LAUNCH_ID = "extra_launch_id"

        fun makeIntent(launchId: String, context: Context): Intent {
            return Intent(context, LaunchDetailsActivity::class.java).putExtra(
                EXTRA_LAUNCH_ID,
                launchId
            )
        }
    }

    private val viewModel: LaunchDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch_details)

        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        intent.extras?.getString(EXTRA_LAUNCH_ID)?.let {
            viewModel.loadLaunchDetails(it)
        }

        retryButton.setOnClickListener { viewModel.retry() }

        viewModel.displayLoading.observe(this, ::displayLoading)
        viewModel.displayError.observe(this, ::displayError)
        viewModel.displayDetails.observe(this, ::displayLaunchDetails)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun displayLoading(loading: Boolean) {
        if (loading) {
            progressBar.visibility = View.VISIBLE
            launchDetailsContainer.visibility = View.GONE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    private fun displayError(error: Boolean) {
        if (error) {
            errorText.visibility = View.VISIBLE
            retryButton.visibility = View.VISIBLE
            launchDetailsContainer.visibility = View.GONE
        } else {
            errorText.visibility = View.GONE
            retryButton.visibility = View.GONE
        }
    }

    private fun displayLaunchDetails(launchDetails: LaunchDetailsDisplay?) {
        launchDetailsContainer.visibility = View.VISIBLE
        launchDetails?.run {
            title = missionName
            launchDateValue.text = launchDate
            launchSiteValue.text = launchSite
            launchSuccessValue.text = getString(launchSuccess)
            launchDetailsValue.text = launchDescription

            if (launchDescription == null) {
                launchDetailsLabel.visibility = View.GONE
                launchDetailsValue.visibility = View.GONE
            }

            if (bannerImageUrl != null) {
                Glide.with(missionImage).load(bannerImageUrl).into(missionImage)
            } else {
                missionImage.visibility = View.GONE
            }

            if (missionPatchUrl != null) {
                Glide.with(missionPatchImage).load(missionPatchUrl).into(missionPatchImage)
            } else {
                missionPatchImage.visibility = View.GONE
            }

            this@LaunchDetailsActivity.rocketName.text = rocketName
            this@LaunchDetailsActivity.rocketDescription.text = rocketDescription
            heightValue.text = getString(R.string.meters, rocketHeight)
            diameterValue.text = getString(R.string.meters, rocketDiameter)
            engineValue.text = rocketEngines

            if (rocketWikipedia != null) {
                moreInfoButton.visibility = View.VISIBLE
                moreInfoButton.setOnClickListener { routeToWikipedia(rocketWikipedia) }
            } else {
                moreInfoButton.visibility = View.GONE
            }
        }
    }

    private fun routeToWikipedia(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        } catch (e: Exception) {
            // Usually occurs because no browser is installed, or is updating
            Toast.makeText(this, R.string.error_routing, Toast.LENGTH_LONG).show()
        }
    }
}