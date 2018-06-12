package simon.trebis.ui.main

import android.annotation.SuppressLint
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import simon.trebis.R
import simon.trebis.data.entity.Website

class MainFragmentView(val view: View) {

    private val recyclerView: RecyclerView = view.findViewById(R.id.layout_list)
    private val fab: FloatingActionButton = view.findViewById(R.id.mainFragment_fab)
    private val counter: TextView = view.findViewById(R.id.registered_count)

    var createWebsite: () -> Unit = {}
    var goToWebsite: (Website) -> Unit = {}
    var goToEditWebsite: (Website) -> Unit = {}
    var deleteWebsite: (Website) -> Unit = {}

    init {
        setupFab()
        setupLayoutManager()
    }

    private fun setupFab() {
        fab.setOnClickListener({ createWebsite() })
    }

    private fun setupLayoutManager() {
        val layoutManager = UnscrollableLayoutManager(view.context)
        layoutManager.setScrollEnabled(false)

        recyclerView.layoutManager = layoutManager
    }

    fun setLayouts(layouts: ArrayList<Website>) {
        val adapter = LayoutAdapter(layouts, view.context)
        adapter.goToWebsite = goToWebsite
        adapter.goToEditWebsite = goToEditWebsite
        adapter.deleteWebsite = deleteWebsite
        recyclerView.adapter = adapter
    }

    fun refreshLayouts() {
        recyclerView.adapter.notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
    fun setCounter(count: Int) {
        counter.text = "($count)"
    }

}
