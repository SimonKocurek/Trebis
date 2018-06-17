package simon.trebis.ui.website

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import simon.trebis.R
import simon.trebis.data.DatabaseManager
import simon.trebis.data.entity.Entry
import simon.trebis.file.FileUtils

class WebsiteViewAdapter(
        private val entries: List<Entry>,
        private val applicationContext: Context
) :
        RecyclerView.Adapter<WebsiteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WebsiteViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.website_list_item, parent, false)

        return WebsiteViewHolder(view)
    }

    override fun onBindViewHolder(holder: WebsiteViewHolder, position: Int) {
        val entry = entries[position]

        holder.setCreationDate(entry.date)
        holder.setPhoto(FileUtils(applicationContext).fileUri(entry.id!!))
        holder.setDeleteAction {
            DatabaseManager.instance(applicationContext).deleteEntry(entry)
            FileUtils(applicationContext).remove(entry.id!!)
        }
    }

    override fun getItemCount() = entries.size

}
