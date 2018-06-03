package simon.trebis.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.recyclerview.R.attr.layoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import simon.trebis.R
import simon.trebis.data.TrebisDatabase
import simon.trebis.data.entity.Website
import android.support.v7.widget.DividerItemDecoration
import android.widget.TextView
import simon.trebis.data.dao.WebsiteDao


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: MainViewModel
    private lateinit var websiteDao: WebsiteDao
    private lateinit var fab: FloatingActionButton
    private lateinit var counter: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)

        counter = view.findViewById(R.id.registered_count)

        setupLayoutManager(view)
        setupFab(view)

        return view
    }

    private fun setupLayoutManager(view: View) {
        val layoutManager = UnscrollableLayoutManager(context!!)
        layoutManager.setScrollEnabled(false)

        recyclerView = view.findViewById(R.id.layout_list)
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, layoutManager.orientation))
    }

    private fun setupFab(view: View) {
        fab = view.findViewById(R.id.mainFragment_fab)
        fab.setOnClickListener({
            val website = Website()
            website.name = (Math.random() * Integer.MAX_VALUE).toString()

            Thread {
                websiteDao.insert(website)
            }.start()
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val database = TrebisDatabase.getDatabase(context!!)
        websiteDao = database.websiteDao()

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        recyclerView.adapter = LayoutAdapter(viewModel.layouts)

        observeDataSource()
    }

    private fun observeDataSource() {
        websiteDao.getAll().observe(this, Observer {
            viewModel.layouts.clear()
            viewModel.layouts.addAll(it!!)
            recyclerView.adapter.notifyDataSetChanged()

            counter.text = "(${it.size})"
        })
    }

}
