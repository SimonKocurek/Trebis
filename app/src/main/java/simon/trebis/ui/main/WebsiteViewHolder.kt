package simon.trebis.ui.main

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import simon.trebis.R
import java.util.*

class WebsiteViewHolder(val view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

    var wrapper: CardView = view.findViewById(R.id.layout_list_main_content)
    var image: ImageView = view.findViewById(R.id.layout_list_image_view)
    var name: TextView = view.findViewById(R.id.layout_list_name)
    var url: TextView = view.findViewById(R.id.layout_list_description)
    var date: TextView = view.findViewById(R.id.layout_list_date)
    var menu: CardView = view.findViewById(R.id.layout_item_button)

    lateinit var openWebsite: () -> Unit
    lateinit var editWebsite: () -> Unit
    lateinit var deleteWebsite: () -> Unit
    lateinit var context: Context

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

    fun setClickCallback(context: Context,
                         openFunction: () -> Unit,
                         editFunction: () -> Unit,
                         deleteFunction: () -> Unit) {
        this.openWebsite = openFunction
        this.editWebsite = editFunction
        this.deleteWebsite = deleteFunction
        this.context = context

        wrapper.setOnClickListener(this)
        menu.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view) {
            menu -> onMenuClick()
            else -> openWebsite()
        }
    }

    private fun onMenuClick() {
        val popup = PopupMenu(context, menu)
        popup.setOnMenuItemClickListener(onMenuItemClick())

        popup.menuInflater.inflate(R.menu.layout_item_menu, popup.menu)
        popup.show()
    }

    private fun onMenuItemClick(): (MenuItem) -> Boolean {
        return {
            when (it.itemId) {
                R.id.layout_item_menu_open -> {
                    openWebsite()
                    true
                }
                R.id.layout_item_menu_edit -> {
                    editWebsite()
                    true
                }
                R.id.layout_item_menu_remove -> {
                    deleteWebsite()
                    true
                }
                else -> false
            }
        }
    }

}
