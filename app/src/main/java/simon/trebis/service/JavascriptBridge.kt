package simon.trebis.service

import android.graphics.Bitmap
import android.graphics.Canvas
import android.webkit.JavascriptInterface
import android.webkit.WebView
import simon.trebis.data.DatabaseManager
import java.io.ByteArrayOutputStream

class JavascriptBridge(
        private val websiteId: Long,
        private val databaseManager: DatabaseManager,
        private val webView: WebView
) {

    companion object {
        const val JAVASCRIPT_APP = "TREBIS_APP"
        const val javascriptCallback = "javascript:$JAVASCRIPT_APP.capture()"
    }

    @JavascriptInterface
    fun capture() {
        Thread.sleep(2500)
        webView.buildDrawingCache()
        Thread.sleep(2500)

        val bitmap = Bitmap.createBitmap(webView.measuredWidth, webView.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        webView.draw(canvas)
        webView.destroyDrawingCache()

        ByteArrayOutputStream().apply {
            BitmapCropper().crop(bitmap).compress(Bitmap.CompressFormat.PNG, 100, this)
            flush()
            close()
            databaseManager.createEntry(websiteId, toByteArray())
        }
    }

}
