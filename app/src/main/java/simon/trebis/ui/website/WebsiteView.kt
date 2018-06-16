package simon.trebis.ui.website

import android.support.v7.widget.RecyclerView
import android.view.View
import simon.trebis.R
import simon.trebis.data.entity.Entry
import simon.trebis.ui.main.UnscrollableLayoutManager

class WebsiteView(view: View) {

    private val recyclerView: RecyclerView = view.findViewById(R.id.website_list)

    init {
        UnscrollableLayoutManager(view.context).let {
            it.setScrollEnabled(false)
            recyclerView.layoutManager = it
        }

//        viewAdapter = MyAdapter(myDataset)
//
//        recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view).apply {
//            // use this setting to improve performance if you know that changes
//            // in content do not change the layout size of the RecyclerView
//            setHasFixedSize(true)
//
//            // use a linear layout manager
//            layoutManager = viewManager
//
//            // specify an viewAdapter (see also next example)
//            adapter = viewAdapter
//
//        }
    }

    var viewModel: WebsiteViewModel? = null

    fun setEntries(entries: List<Entry>) {
        viewModel?.let { it.entries = entries }
    }

}
