package simon.trebis.ui.website

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import simon.trebis.R
import simon.trebis.file.FileUtils

class WebsiteViewAdapter(
        private val viewModel: WebsiteViewModel,
        private val applicationContext: Context
) :
        RecyclerView.Adapter<WebsiteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WebsiteViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.website_list_item, parent, false)

        return WebsiteViewHolder(view)
    }

    override fun onBindViewHolder(holder: WebsiteViewHolder, position: Int) {
        val entry = viewModel.entries[position]

        holder.setCreationDate(entry.date)
        val bitmap = FileUtils(applicationContext).read(entry.id!!)
        holder.setPhoto(bitmap)
    }

    override fun getItemCount() = viewModel.entries.size

}
