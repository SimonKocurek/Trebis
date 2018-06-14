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
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.layout_list_item, parent, false)
        return WebsiteViewHolder(view)
    }

    override fun onBindViewHolder(holder: WebsiteViewHolder, position: Int) {
        val website = dataset[position]
        holder.also {
            it.setImage(website.favicon)
            it.setName(website.name)
            it.setUrl(website.url)
            it.setDate(website.date, dateFormat)
            it.goToWebsite = { goToWebsite(website) }
            it.goToEditWebsite = { goToEditWebsite(website) }
            it.deleteWebsite = { deleteWebsite(website) }
            it.context = context
        }
    }

    override fun getItemCount() = dataset.size
}