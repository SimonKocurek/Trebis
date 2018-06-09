package simon.trebis.ui.main;

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import simon.trebis.R
import simon.trebis.data.entity.Website


class LayoutAdapter(private val dataset: List<Website>,
                    private val context: Context,
                    private val navigateToWebsite: (Website) -> Unit,
                    private val editWebsite: (Website) -> Unit,
                    private val deleteWebsite: (Website) -> Unit) :
        RecyclerView.Adapter<WebsiteViewHolder>() {

    private val dateFormat = DateFormat.getDateFormat(context.applicationContext)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WebsiteViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_list_item, parent, false)
        return WebsiteViewHolder(view)
    }

    override fun onBindViewHolder(holder: WebsiteViewHolder, position: Int) {
        val website = dataset[position]

        website.iconPath
        holder.setImage()
        holder.setName(website.name)
        holder.setUrl(website.url)
        holder.setDate(website.date, dateFormat)
        holder.setClickCallback(
                context,
                { navigateToWebsite(website) },
                { editWebsite(website) },
                { deleteWebsite(website) }
        )
    }

    override fun getItemCount() = dataset.size
}