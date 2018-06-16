package simon.trebis.ui.website

import android.support.v7.widget.RecyclerView
import android.view.View
import simon.trebis.R
import simon.trebis.data.entity.Entry
import simon.trebis.ui.main.UnscrollableLayoutManager

class WebsiteView(val view: View) {

    private val recyclerView: RecyclerView = view.findViewById(R.id.website_list)

    var viewModel: WebsiteViewModel? = null
        set(value) {
            field = value
            refreshRecyclerView()
        }

    private fun refreshRecyclerView() {
        recyclerView.apply {
            layoutManager = UnscrollableLayoutManager(view.context).apply { setScrollEnabled(false) }
            adapter = WebsiteViewAdapter(viewModel!!)
        }
    }

    fun setEntries(entries: List<Entry>) {
        viewModel?.let {
            it.entries = entries
            recyclerView.adapter.notifyDataSetChanged()
        }
    }

}
