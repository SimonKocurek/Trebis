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
import simon.trebis.R
import simon.trebis.data.DatabaseManager
import simon.trebis.data.entity.Website
import simon.trebis.ui.Consts.Companion.WEBSITE_ID_KEY
import simon.trebis.ui.SortType


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

        mainFragmentView = MainFragmentView(view)
        mainFragmentView.changeSortMethod = { changeSortMethod() }
        mainFragmentView.createWebsite = { createWebsite() }
        mainFragmentView.goToWebsite = { website -> goToWebsite(website) }
        mainFragmentView.goToEditWebsite = { website -> goToEditWebsite(website) }
        mainFragmentView.deleteWebsite = { website -> deleteWebsiteWithDialog(website) }

        setHasOptionsMenu(true)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        navController = Navigation.findNavController(view!!)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mainFragmentView.setLayouts(viewModel.layouts, viewModel.sortType)
        mainFragmentSearchView?.setQuery(viewModel.filter)

        observeWebsites()
    }

    private fun observeWebsites() {
        val websites = DatabaseManager.instance(context!!).getAllWebsites()
        websites.observe(this, Observer { onWebsitesChanged(it) })
    }

    private fun onWebsitesChanged(it: List<Website>?) {
        if (it != null) {
            viewModel.layouts.clear()
            viewModel.layouts.addAll(it)
            mainFragmentView.setLayouts(viewModel.layouts, viewModel.sortType)
        }
    }

    private fun changeSortMethod() {
        AlertDialog.Builder(context!!)
                .setTitle(getString(R.string.sortby))
                .setItems(R.array.sort_methods, { _, idx ->
                    viewModel.sortType = SortType.withIndex(idx)!!
                    mainFragmentView.setLayouts(viewModel.layouts, viewModel.sortType)
                }).show()
    }

    private fun deleteWebsiteWithDialog(website: Website) {
        AlertDialog.Builder(context!!)
                .setTitle(R.string.deletewebsite)
                .setPositiveButton(R.string.delete, { _, _ -> databaseManager.deleteWebsite(website) })
                .setNegativeButton(R.string.cancel, { _, _ -> {} })
                .show()
    }

    private fun createWebsite() {
        launch(UI) {
            val id = databaseManager.createWebsite().await()
            goTo(id!!.toInt(), R.id.mainFragment_to_createWebsite)
        }
    }

    private fun goToWebsite(website: Website) {
        goTo(website.id!!, R.id.mainFragment_to_website)
    }

    private fun goToEditWebsite(website: Website) {
        goTo(website.id!!, R.id.mainFragment_to_createWebsite)
    }

    private fun goTo(websiteId: Int, @IdRes resId: Int) {
        val bundle = Bundle().also { it.putInt(WEBSITE_ID_KEY, websiteId) }
        navController.navigate(resId, bundle)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        val searchItem = menu.findItem(R.id.app_bar_search)
        mainFragmentSearchView = MainFragmentSearch(searchItem)
        mainFragmentSearchView?.setQuery(viewModel.filter)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.toolbar_settings -> {
                Navigation.findNavController(view!!).navigate(R.id.mainFragment_to_preferences)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
