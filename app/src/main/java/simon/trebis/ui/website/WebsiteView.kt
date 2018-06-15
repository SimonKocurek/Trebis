package simon.trebis.ui.website

import android.content.Context
import android.graphics.BitmapFactory
import android.view.View
import com.github.chrisbanes.photoview.PhotoView
import simon.trebis.R
import simon.trebis.data.DatabaseManager

class WebsiteView(root: View, val context: Context, val databaseManager: DatabaseManager) {

    private val image: PhotoView = root.findViewById(R.id.website_image)

    var viewModel: WebsiteViewModel? = null

    fun refresh() {
        viewModel?.entries?.let { entries ->
            if (entries.isNotEmpty()) {
                entries.last().snapshot.let {
                    val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
                    image.setImageBitmap(bitmap)
                }
            }
        }
    }

}
