package simon.trebis.ui.main

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import simon.trebis.R
import java.util.*

class WebsiteViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    var image: ImageView = view.findViewById(R.id.layout_list_image_view)
    var name: TextView = view.findViewById(R.id.layout_list_name)
    var url: TextView = view.findViewById(R.id.layout_list_description)
    var date: TextView = view.findViewById(R.id.layout_list_date)
    var menu: ImageButton = view.findViewById(R.id.layout_item_button)

    fun setImage() {

    }

    fun setName(name: String) {
        if (name.isNotBlank()) {
            this.name.text = name
        }
    }

    fun setUrl(url: String) {
        if (url.isNotBlank()) {
            this.url.text = url
        }
    }

    fun setDate(date: Date, format: java.text.DateFormat) {
        this.date.text = format.format(date)

    }

    fun setMenuActions(context: Context,
                       openFunction: () -> Unit,
                       deleteFunction: () -> Unit) {

        view.setOnClickListener({
            when (it.id) {
                R.id.layout_item_button -> onMenuClick(context, openFunction, deleteFunction)
                else -> openFunction()
            }
        })
    }

    private fun onMenuClick(context: Context, openFunction: () -> Unit, deleteFunction: () -> Unit) {
        val popup = PopupMenu(context, menu)
        popup.setOnMenuItemClickListener({
            when (it.itemId) {
                R.id.layout_item_menu_open -> {
                    openFunction()
                    true
                }
                R.id.layout_item_menu_remove -> {
                    deleteFunction()
                    true
                }
                else -> false
            }
        })

        popup.menuInflater.inflate(R.menu.layout_item_menu, popup.menu)
        popup.show()
    }

}
