package simon.trebis.ui.main

import android.annotation.SuppressLint
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import simon.trebis.R
import simon.trebis.data.entity.Website
import simon.trebis.ui.SortType

class MainFragmentView(val view: View) {

    private val sortButton: CardView = view.findViewById(R.id.layout_sort_button)
    private val recyclerView: RecyclerView = view.findViewById(R.id.layout_list)
    private val fab: FloatingActionButton = view.findViewById(R.id.mainFragment_fab)
    private val counter: TextView = view.findViewById(R.id.registered_count)

    var changeSortMethod: () -> Unit = {}
    var createWebsite: () -> Unit = {}
    var goToWebsite: (Website) -> Unit = {}
    var goToEditWebsite: (Website) -> Unit = {}
    var deleteWebsite: (Website) -> Unit = {}

    init {
        setupSortButton()
        setupFab()
        setupLayoutManager()
    }

    private fun setupSortButton() {
        sortButton.setOnClickListener { changeSortMethod() }
    }

    private fun setupFab() {
        fab.setOnClickListener { createWebsite() }
    }

    private fun setupLayoutManager() {
        val layoutManager = UnscrollableLayoutManager(view.context)
        layoutManager.setScrollEnabled(false)

        recyclerView.layoutManager = layoutManager
    }

    fun setLayouts(layouts: ArrayList<Website>, sortType: SortType, filter: String) {
        val displayed = layouts
                .filter { it.name.contains(filter) }
                .sortedWith(sortType.comparator)

        recyclerView.adapter = LayoutAdapter(displayed, view.context).also {
            it.goToWebsite = goToWebsite
            it.goToEditWebsite = goToEditWebsite
            it.deleteWebsite = deleteWebsite
        }

        setCounter(displayed.size)
    }

    @SuppressLint("SetTextI18n")
    private fun setCounter(count: Int) {
        counter.text = "($count)"
    }

}
