package simon.trebis.ui.website

import android.graphics.PointF
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.text.format.DateFormat
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.ImageViewState
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import simon.trebis.R
import java.util.*

class WebsiteViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    private val deleteButton: Button = view.findViewById(R.id.website_item_remove)
    private val creationDate: TextView = view.findViewById(R.id.website_item_date)
    private val photo: SubsamplingScaleImageView = view.findViewById(R.id.website_item_photo)

    fun setCreationDate(date: Date) {
        creationDate.text = DateFormat.format("HH:mm:ss", date)
    }

    fun setPhoto(uri: Uri) {
        photo.setImage(ImageSource.uri(uri), ImageViewState(
                photo.width.toFloat(),
                PointF(0f, 0f),
                SubsamplingScaleImageView.ORIENTATION_USE_EXIF
        ))
    }

    fun setDeleteAction(onButtonClick: (View) -> Unit) {
        deleteButton.setOnClickListener(onButtonClick)
    }

}
