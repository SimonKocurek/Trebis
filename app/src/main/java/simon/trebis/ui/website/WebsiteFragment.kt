package simon.trebis.ui.website

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import androidx.navigation.NavController
import androidx.navigation.Navigation
import simon.trebis.Const.Companion.WEBSITE_ID_KEY
import simon.trebis.R
import simon.trebis.data.DatabaseManager


class WebsiteFragment : Fragment() {

    companion object {
        fun newInstance() = WebsiteFragment()
    }

    private lateinit var databaseManager: DatabaseManager
    private lateinit var websiteView: WebsiteView
    private lateinit var websiteFragmentCalendar: WebsiteFragmentCalendar
    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.website_fragment, container, false)

        context?.let {
            databaseManager = DatabaseManager.instance(it)
            websiteView = WebsiteView(view, it, databaseManager)
        }
        setHasOptionsMenu(true)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        websiteView.viewModel = ViewModelProviders
                .of(this)
                .get(WebsiteViewModel::class.java)
                .also { observeEntries(it) }

        websiteView.refresh()
        navController = Navigation.findNavController(view!!)
    }

    private fun observeEntries(viewModel: WebsiteViewModel) {
        arguments?.getLong(WEBSITE_ID_KEY)?.let { id ->
            databaseManager.getEntriesForWebsite(id).observe(this, Observer { entries ->
                entries?.let {
                    viewModel.entries = it
                    websiteView.refresh()
                }
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)

        val calendarItem = menu.findItem(R.id.app_bar_calendar)
        websiteFragmentCalendar = WebsiteFragmentCalendar(context!!, calendarItem, websiteView.viewModel)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.app_bar_settings -> {
                navController.navigate(R.id.website_to_preferences)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
