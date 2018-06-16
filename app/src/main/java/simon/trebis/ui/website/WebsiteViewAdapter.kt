package simon.trebis.ui.website

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import simon.trebis.R

class WebsiteViewAdapter(private val viewModel: WebsiteViewModel) :
        RecyclerView.Adapter<WebsiteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WebsiteViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.website_list_item, parent, false)

        return WebsiteViewHolder(view)
    }

    override fun onBindViewHolder(holder: WebsiteViewHolder, position: Int) {
        val entry = viewModel.entries[position]

        holder.setCreationDate(entry.date)
        holder.setPhoto(entry.snapshot)
    }

    override fun getItemCount() = viewModel.entries.size

}
