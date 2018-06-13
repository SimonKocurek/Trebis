package simon.trebis.ui.main;

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import simon.trebis.R
import simon.trebis.data.entity.Website


class LayoutAdapter(private val dataset: List<Website>, private val context: Context) :
        RecyclerView.Adapter<WebsiteViewHolder>() {

    var goToWebsite: (Website) -> Unit = {}
    var goToEditWebsite: (Website) -> Unit = {}
    var deleteWebsite: (Website) -> Unit = {}

    private val dateFormat = DateFormat.getDateFormat(context.applicationContext)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WebsiteViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_list_item, parent, false)
        return WebsiteViewHolder(view)
    }

    override fun onBindViewHolder(holder: WebsiteViewHolder, position: Int) {
        val website = dataset[position]

        holder.setImage(website.id!!, website.iconPath)
        holder.setName(website.name)
        holder.setUrl(website.url)
        holder.setDate(website.date, dateFormat)
        holder.goToWebsite = { goToWebsite(website) }
        holder.goToEditWebsite = { goToEditWebsite(website) }
        holder.deleteWebsite = { deleteWebsite(website) }
        holder.context = context
    }

    override fun getItemCount() = dataset.size
}