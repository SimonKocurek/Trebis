package simon.trebis.service

import android.graphics.Bitmap
import android.graphics.Canvas
import android.webkit.JavascriptInterface
import android.webkit.WebView
import simon.trebis.data.DatabaseManager
import java.io.ByteArrayOutputStream
import kotlin.math.max
import kotlin.math.min

class JavascriptBridge(
        private val websiteId: Long,
        private val databaseManager: DatabaseManager,
        private val downloadServiceHandler: DownloadServiceHandler,
        private val webView: WebView
) {

    companion object {
        const val JAVASCRIPT_APP = "TREBIS_APP"

        val javascriptCallback = """
            javascript:(function() {
                var body = document.body;
                var html = document.documentElement;

                var height = Math.max( body.scrollHeight, body.offsetHeight,
                                       html.clientHeight, html.scrollHeight, html.offsetHeight );

                $JAVASCRIPT_APP.resizeAndCapture(height);
            })()
        """.trimIndent()
    }

    @JavascriptInterface
    fun resizeAndCapture(height: Float) {
        downloadServiceHandler.let {
            val newHeight = max(it.height / 2, min(height.toInt(), it.height * 3))

            webView.measure(it.width, newHeight)
            webView.layout(0, 0, it.width, newHeight)
        }

        takeWebViewScreenshot()
    }

    private fun takeWebViewScreenshot() {
        Thread.sleep(20000)

        val bitmap = Bitmap.createBitmap(webView.measuredWidth, webView.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        webView.draw(canvas)

        val byteStream = ByteArrayOutputStream()
        BitmapCropper().crop(bitmap).compress(Bitmap.CompressFormat.PNG, 100, byteStream)

        databaseManager.createEntry(websiteId, byteStream.toByteArray())
        downloadServiceHandler.stopService()
    }

}
