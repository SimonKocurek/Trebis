package simon.trebis.ui.website

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import simon.trebis.R
import simon.trebis.data.entity.Entry
import simon.trebis.ui.main.UnscrollableLayoutManager

class WebsiteView(val view: View) {

    private val recyclerView: RecyclerView = view.findViewById(R.id.website_list)
    var applicationContext: Context? = null
    var viewModel: WebsiteViewModel? = null

    fun refreshRecyclerView() {
        recyclerView.apply {
            layoutManager = UnscrollableLayoutManager(view.context).apply { setScrollEnabled(false) }

            viewModel?.let { model ->
                applicationContext?.let { context ->
                    adapter = WebsiteViewAdapter(model, context)
                }
            }
        }
    }

    fun setEntries(entries: List<Entry>) {
        viewModel?.let {
            it.entries = entries
            recyclerView.adapter?.let { it.notifyDataSetChanged() }
        }
    }

}
