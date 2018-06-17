package simon.trebis.ui.website

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import simon.trebis.Const.Companion.WEBSITE_ID_KEY
import simon.trebis.MainActivity
import simon.trebis.R
import simon.trebis.data.DatabaseManager
import simon.trebis.data.entity.Entry


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
            websiteView = WebsiteView(view)
        }
        setHasOptionsMenu(true)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        websiteView.let {
            it.viewModel = ViewModelProviders
                    .of(this)
                    .get(WebsiteViewModel::class.java)
            it.applicationContext = activity?.applicationContext
            it.setEntries(ArrayList())
            it.refreshRecyclerView()
        }

        navController = Navigation.findNavController(view!!)
        observeEntries()
    }

    private fun observeEntries() {
        launch(UI) {
            arguments?.getLong(WEBSITE_ID_KEY)?.let { id ->
                setFragmentTitle(id)
                getEntries(id)
            }
        }
    }

    private suspend fun setFragmentTitle(websiteId: Long) {
        databaseManager
                .getWebsite(websiteId)
                .await()
                ?.let { (activity as MainActivity).setActionBarTitle(it.name) }
    }

    private suspend fun getEntries(websiteId: Long) {
        databaseManager
                .getEntries(websiteId)
                .await()
                .observe(this, Observer { setEntries(it) })
    }

    private fun setEntries(entries: List<Entry>?) {
        entries?.let {
            websiteView.setEntries(it)
            websiteView.refreshRecyclerView()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)

        val calendarItem = menu.findItem(R.id.app_bar_calendar)
        websiteFragmentCalendar = WebsiteFragmentCalendar(
                context!!,
                calendarItem,
                websiteView,
                websiteView.viewModel
        )

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
