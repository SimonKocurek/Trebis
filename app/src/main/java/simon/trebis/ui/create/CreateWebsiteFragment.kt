package simon.trebis.ui.create

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import simon.trebis.Const.Companion.NO_ID
import simon.trebis.Const.Companion.WEBSITE_ID_KEY
import simon.trebis.MainActivity
import simon.trebis.R
import simon.trebis.data.DatabaseManager
import simon.trebis.data.entity.Website
import simon.trebis.job.TrebisDownloadJob


class CreateWebsiteFragment : Fragment() {

    companion object {
        fun newInstance() = CreateWebsiteFragment()
    }

    private lateinit var viewModel: CreateWebsiteViewModel
    private lateinit var websiteView: CreateWebsiteView
    private lateinit var databaseManager: DatabaseManager
    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.create_website_fragment, container, false)

        databaseManager = DatabaseManager.instance(context!!)
        websiteView = CreateWebsiteView(view, activity as MainActivity).also {
            it.onConfirm = { website -> confirmChanges(website) }
        }
        setHasOptionsMenu(true)

        return view
    }

    private fun confirmChanges(website: Website) {
        if (website.id == null) {
            createWebsite(website)
        } else {
            updateWebsite(website)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(CreateWebsiteViewModel::class.java)
        navController = Navigation.findNavController(view!!)
        arguments?.getLong(WEBSITE_ID_KEY)?.let { getWebsite(it) }
    }

    private fun getWebsite(id: Long) {
        if (id == NO_ID) {
            val temporalWebsite = Website()
            websiteView.updateWith(temporalWebsite)
        } else {
            observeWebsite(id)
        }
    }

    private fun observeWebsite(websiteId: Long) {
        val website = databaseManager.getWebsite(websiteId)
        website.observe(this, Observer { websiteView.updateWith(it) })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.toolbar_settings -> {
                Navigation.findNavController(view!!).navigate(R.id.createWebsite_to_preference)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateWebsite(website: Website) {
        launch(UI) {
            updateJob(website.id!!, website.url)
            databaseManager.updateWebsite(website)
            navController.popBackStack()
        }
    }

    private suspend fun updateJob(websiteId: Long, url: String) {
        databaseManager.getJobForWebsite(websiteId).await()?.let {
            TrebisDownloadJob().apply {
                cancelById(it.schedulerId)
                it.schedulerId = schedule(websiteId, url)
                databaseManager.updateJob(it)
            }
        }
    }

    private fun createWebsite(website: Website) {
        launch(UI) {
            databaseManager.createWebsite(website).await()?.let {
                scheduleNewJob(it, website.url)
            }

            navController.popBackStack()
        }
    }

    private fun scheduleNewJob(websiteId: Long, url: String): Deferred<Long?> {
        TrebisDownloadJob().schedule(websiteId, url).let {
            return databaseManager.createJob(websiteId, it)
        }
    }

    override fun onStop() {
        super.onStop()
        websiteView.fragmentStopped()
    }

}
