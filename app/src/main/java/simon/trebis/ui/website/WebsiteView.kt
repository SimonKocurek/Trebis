package simon.trebis.ui.website

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import simon.trebis.R
import simon.trebis.data.entity.Entry
import simon.trebis.ui.UnscrollableLayoutManager
import java.util.*

class WebsiteView(val view: View) {

    private val recyclerView: RecyclerView = view.findViewById(R.id.website_list)
    var context: Context? = null
    var viewModel: WebsiteViewModel? = null

    fun refreshRecyclerView() {
        recyclerView.apply {
            layoutManager = UnscrollableLayoutManager(view.context, LinearLayoutManager.HORIZONTAL)

            viewModel?.let { model ->
                this@WebsiteView.context?.let { context ->
                    val entries = model.entries.filter { entry -> entry.date.sameAs(model.selectedDate) }
                    adapter = WebsiteViewAdapter(entries, context)
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

    private fun Date.sameAs(other: Date) = year == other.year
            && month == other.month
            && day == other.day

}
