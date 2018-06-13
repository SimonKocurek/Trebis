package simon.trebis.ui.main

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import simon.trebis.R
import simon.trebis.service.DiskUtils
import java.util.*

class WebsiteViewHolder(val view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

    val wrapper: CardView = view.findViewById(R.id.layout_list_main_content)
    val image: ImageView = view.findViewById(R.id.layout_list_image_view)
    val name: TextView = view.findViewById(R.id.layout_list_name)
    val url: TextView = view.findViewById(R.id.layout_list_description)
    val date: TextView = view.findViewById(R.id.layout_list_date)
    val menu: CardView = view.findViewById(R.id.layout_item_button)

    lateinit var goToWebsite: () -> Unit
    lateinit var goToEditWebsite: () -> Unit
    lateinit var deleteWebsite: () -> Unit
    lateinit var context: Context

    init {
        wrapper.setOnClickListener(this)
        menu.setOnClickListener(this)
    }

    fun setImage(id: Int, path: String) {
        if (path.isBlank()) {
            return
        }

        val file = DiskUtils.fileFor(id, path, context)
        val bitmapJob = DiskUtils.getBitmapFromFile(file, context)

        launch(UI) {
            val bitmap = bitmapJob.await()
            image.setImageBitmap(bitmap)
        }
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

    override fun onClick(view: View) {
        when (view) {
            menu -> onMenuClick()
            else -> goToWebsite()
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
                    goToWebsite();true
                }
                R.id.layout_item_menu_edit -> {
                    goToEditWebsite();true
                }
                R.id.layout_item_menu_remove -> {
                    deleteWebsite();true
                }
                else -> false
            }
        }
    }

}
