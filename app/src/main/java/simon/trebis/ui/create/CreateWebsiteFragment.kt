package simon.trebis.ui.create

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import simon.trebis.Const.Companion.NO_ID
import simon.trebis.Const.Companion.WEBSITE_ID_KEY
import simon.trebis.MainActivity
import simon.trebis.R
import simon.trebis.data.DatabaseManager
import simon.trebis.data.entity.Website
import simon.trebis.work.DownloadManager

class CreateWebsiteFragment : Fragment() {

    companion object {
        fun newInstance() = CreateWebsiteFragment()
    }

    private lateinit var websiteView: CreateWebsiteView
    private lateinit var databaseManager: DatabaseManager
    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.create_website_fragment, container, false)

        databaseManager = DatabaseManager.instance(context!!)
        websiteView = CreateWebsiteView(view, activity as MainActivity).apply {
            onConfirm = { website -> confirmChanges(website) }
        }
        setHasOptionsMenu(true)

        return view
    }

    private fun confirmChanges(website: Website) {
        launch(UI) {
            if (website.id == null) {
                createWebsite(website)
            } else {
                updateWebsite(website)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navController = Navigation.findNavController(view!!)
        arguments?.getLong(WEBSITE_ID_KEY)?.let { getWebsite(it) }
    }

    private fun getWebsite(id: Long) {
        launch(UI) {
            websiteView.updateWith(
                    if (id == NO_ID) Website()
                    else databaseManager.getWebsite(id).await()!!
            )
        }
    }

    private fun updateWebsite(website: Website) {
        DownloadManager(activity!!.applicationContext).apply { unschedule(website); schedule(website) }
        databaseManager.updateWebsite(website)
        navController.popBackStack()
    }

    private suspend fun createWebsite(website: Website) {
        databaseManager.createWebsite(website).await()?.let {
            website.id = it
            DownloadManager(activity!!.applicationContext).schedule(website)
        }
        navController.popBackStack()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.app_bar_settings -> {
                Navigation.findNavController(view!!).navigate(R.id.createWebsite_to_preference)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onStop() {
        super.onStop()
        websiteView.fragmentStopped()
    }

}
