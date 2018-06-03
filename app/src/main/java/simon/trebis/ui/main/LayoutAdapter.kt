package simon.trebis.ui.main;

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import simon.trebis.R
import simon.trebis.data.entity.Website
import java.text.SimpleDateFormat


class LayoutAdapter(private val dataset: List<Website>) :
        RecyclerView.Adapter<LayoutAdapter.ViewHolder>() {

    private val dateFormatter = SimpleDateFormat("dd/MM/yyyy")

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): LayoutAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_list_item, parent, false)

        view.setOnClickListener({

        })

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val website = dataset[position]

        val image = holder.view.findViewById<ImageView>(R.id.layout_list_image_view)
        val name = holder.view.findViewById<TextView>(R.id.layout_list_name)
        val description = holder.view.findViewById<TextView>(R.id.layout_list_description)
        val date = holder.view.findViewById<TextView>(R.id.layout_list_date)

        name.text = website.name
        description.text = website.url
        date.text = dateFormatter.format(website.date)
    }

    override fun getItemCount() = dataset.size
}