package simon.trebis.ui.main

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.TextView
import androidx.navigation.Navigation
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import simon.trebis.R
import simon.trebis.data.DatabaseManager
import simon.trebis.data.entity.Website


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var databaseManager: DatabaseManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton
    private lateinit var counter: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)

        initializeVariables(view)
        setupLayoutManager(view)
        setupFab()
        setHasOptionsMenu(true)

        return view
    }

    private fun initializeVariables(view: View) {
        recyclerView = view.findViewById(R.id.layout_list)
        counter = view.findViewById(R.id.registered_count)
        fab = view.findViewById(R.id.mainFragment_fab)
        databaseManager = DatabaseManager.instance(view.context)
    }

    private fun setupLayoutManager(view: View) {
        val layoutManager = UnscrollableLayoutManager(view.context)
        layoutManager.setScrollEnabled(false)
        recyclerView.layoutManager = layoutManager
    }

    private fun setupFab() {
        fab.setOnClickListener({
            createWebsite()
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        recyclerView.adapter = LayoutAdapter(
                viewModel.layouts, context!!,
                this@MainFragment::goToWebsite,
                this@MainFragment::editWebsite,
                this@MainFragment::deleteWebsite
        )

        observeDataSource()
    }

    private fun goToWebsite(website: Website) {
        val bundle = Bundle().also { it.putInt("website_id", website.id!!) }
        Navigation.findNavController(view!!).navigate(R.id.mainFragment_to_website, bundle)
    }

    private fun editWebsite(website: Website) {
        val bundle = Bundle().also { it.putInt("website_id", website.id!!) }
        Navigation.findNavController(view!!).navigate(R.id.mainFragment_to_createWebsite, bundle)
    }

    private fun deleteWebsite(website: Website) {
        AlertDialog.Builder(context!!)
                .setTitle(R.string.deletewebsite)
                .setMessage(R.string.deletingwebsite)
                .setIcon(R.drawable.ic_delete_black_24dp)
                .setPositiveButton(R.string.delete, { _, _ -> databaseManager.deleteWebsite(website) })
                .setNegativeButton(R.string.cancel, { _, _ -> {} })
                .show()
    }

    private fun createWebsite() {
        launch(UI) {
            val id = databaseManager.createWebsite().await()

            val bundle = Bundle().also { it.putInt("website_id", id!!.toInt()) }
            Navigation.findNavController(view!!).navigate(R.id.mainFragment_to_createWebsite, bundle)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observeDataSource() {
        val websitesLiveData = DatabaseManager.instance(context!!).getAllWebsites()
        websitesLiveData.observe(this, Observer {
            viewModel.layouts.clear()
            viewModel.layouts.addAll(it!!)
            recyclerView.adapter?.notifyDataSetChanged()

            counter.text = "(${it.size})"
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        menu.findItem(R.id.app_bar_search).isVisible = true
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
