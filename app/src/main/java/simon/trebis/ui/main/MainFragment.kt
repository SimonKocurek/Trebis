package simon.trebis.ui.main

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import simon.trebis.R
import simon.trebis.data.DatabaseManager
import simon.trebis.data.entity.Website


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var viewModel: MainViewModel? = null

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
            databaseManager.createWebsite()
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        recyclerView.adapter = LayoutAdapter(
                viewModel!!.layouts, context!!,
                this@MainFragment::goToWebsite,
                databaseManager::deleteWebsite
        )

        observeDataSource()
    }

    private fun goToWebsite(website: Website) {
        val bundle = Bundle().also { it.putInt("website_id", website.id!!) }
        Navigation.findNavController(view!!).navigate(R.id.action_mainFragment_to_websiteFragment, bundle)
    }

    @SuppressLint("SetTextI18n")
    private fun observeDataSource() {
        val websitesLiveData = DatabaseManager.instance(context!!).getAllWebsites()
        websitesLiveData.observe(this, Observer {
            viewModel?.layouts?.clear()
            viewModel?.layouts?.addAll(it!!)
            recyclerView.adapter?.notifyDataSetChanged()

            counter.text = "(${it?.size})"
        })
    }

}
