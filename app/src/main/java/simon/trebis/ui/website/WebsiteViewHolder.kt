package simon.trebis.ui.website

import android.graphics.BitmapFactory
import android.support.v7.widget.RecyclerView
import android.text.format.DateFormat
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.github.chrisbanes.photoview.PhotoView
import simon.trebis.R
import java.util.*

class WebsiteViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    private val deleteButton: Button = view.findViewById(R.id.website_item_remove)
    private val creationDate: TextView = view.findViewById(R.id.website_item_date)
    private val photo: PhotoView = view.findViewById(R.id.website_item_photo)

    fun setCreationDate(date: Date) {
        creationDate.text = DateFormat.format("HH:mm:ss", date)
    }

    fun setPhoto(bitmap: ByteArray) {
        val imageBitmap = BitmapFactory.decodeByteArray(bitmap, 0, bitmap.size)
        photo.setImageBitmap(imageBitmap)
    }

}
