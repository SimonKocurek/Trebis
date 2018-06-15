package simon.trebis.service

import android.graphics.Bitmap
import android.graphics.Color

/**
 * Source Manzotin: https://stackoverflow.com/a/32797296/5521670
 */
class BitmapCropper {

    fun crop(bitmap: Bitmap): Bitmap {
        val imageHeight = bitmap.height
        val imageWidth = bitmap.width

        var endHeight = 0
        for (y in imageHeight - 1 downTo 0) {
            if (endHeight == 0) {
                for (x in 0 until imageWidth) {
                    if (bitmap.getPixel(x, y) != Color.WHITE) {
                        endHeight = y
                        break
                    }
                }
            } else
                break
        }

        return Bitmap.createBitmap(bitmap, 0, 0, imageWidth, endHeight)
    }

}
