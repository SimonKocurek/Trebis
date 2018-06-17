package simon.trebis.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.*
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import simon.trebis.Const.Companion.NO_ID
import simon.trebis.Const.Companion.WEBSITE_ID_KEY
import simon.trebis.R
import simon.trebis.data.DatabaseManager
import simon.trebis.data.entity.Website
import simon.trebis.file.FileUtils
import simon.trebis.ui.SortType
import simon.trebis.work.DownloadManager


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var databaseManager: DatabaseManager
    private lateinit var mainFragmentView: MainFragmentView
    private lateinit var navController: NavController

    private var mainFragmentSearchView: MainFragmentSearch? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)

        databaseManager = DatabaseManager.instance(view.context)

        mainFragmentView = MainFragmentView(view).also {
            it.changeSortMethod = { changeSortMethod() }
            it.createWebsite = { goToCreateWebsite() }
            it.goToWebsite = { website -> goToWebsite(website) }
            it.goToEditWebsite = { website -> goToEditWebsite(website) }
            it.deleteWebsite = { website -> deleteWebsiteWithDialog(website) }
        }

        setHasOptionsMenu(true)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        navController = Navigation.findNavController(view!!)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        launch(UI) {
            refreshLayout()
            observeWebsites()
        }
    }

    private suspend fun observeWebsites() {
        DatabaseManager
                .instance(context!!)
                .getWebsites()
                .await()
                .observe(this, Observer { onWebsitesChanged(it) })
    }

    private fun onWebsitesChanged(websites: List<Website>?) {
        websites?.let {
            viewModel.layouts.clear()
            viewModel.layouts.addAll(it)

            refreshLayout()
        }
    }

    private fun changeSortMethod() {
        AlertDialog.Builder(context!!)
                .setTitle(getString(R.string.sortby))
                .setItems(R.array.sort_methods) { _, idx ->
                    viewModel.sortType = SortType.withIndex(idx)!!
                    refreshLayout()
                }.show()
    }

    private fun deleteWebsiteWithDialog(website: Website) {
        AlertDialog.Builder(context!!)
                .setTitle(R.string.deletewebsite)
                .setPositiveButton(R.string.remove) { _, _ -> deleteWebsite(website) }
                .setNegativeButton(R.string.cancel) { _, _ -> run {} }
                .show()
    }

    private fun deleteWebsite(website: Website) {
        launch {
            deleteEntries(website)
            databaseManager.deleteWebsite(website)
            DownloadManager().unschedule(website)
        }
    }

    private suspend fun deleteEntries(website: Website) {
        databaseManager
                .getEntries(website.id!!)
                .await()
                .observe(this, Observer {
                    it?.let { entries ->
                        val files = FileUtils(activity!!.applicationContext)
                        entries.forEach { entry -> files.remove(entry.id!!) }
                    }
                })
    }

    private fun goToCreateWebsite() {
        goTo(NO_ID, R.id.mainFragment_to_createWebsite)
    }

    private fun goToWebsite(website: Website) {
        goTo(website.id!!, R.id.mainFragment_to_website)
    }

    private fun goToEditWebsite(website: Website) {
        goTo(website.id!!, R.id.mainFragment_to_createWebsite)
    }

    private fun goTo(websiteId: Long, @IdRes resId: Int) {
        val bundle = Bundle().also { it.putLong(WEBSITE_ID_KEY, websiteId) }
        navController.navigate(resId, bundle)
    }

    private fun refreshLayout() {
        mainFragmentView.setLayouts(viewModel.layouts, viewModel.sortType, viewModel.filter)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        val searchItem = menu.findItem(R.id.app_bar_search)

        mainFragmentSearchView = MainFragmentSearch(searchItem, viewModel).also {
            it.onFilterChange = { filter ->
                viewModel.filter = filter
                refreshLayout()
            }
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.app_bar_settings -> {
                Navigation.findNavController(view!!).navigate(R.id.mainFragment_to_preferences)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
