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
        downloadServiceHandler.dimensions()?.let {
            webView.measure(it.widthPixels, (it.density * height).toInt())
            webView.layout(0, 0, it.widthPixels, (it.density * height).toInt())
        }

        takeWebViewScreenshot()
    }

    private fun takeWebViewScreenshot() {
        Thread.sleep(20000)

        val bitmap = Bitmap.createBitmap(webView.measuredWidth, webView.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        webView.draw(canvas)

        val byteStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteStream)

        databaseManager.createEntry(websiteId, byteStream.toByteArray())
        downloadServiceHandler.stopService()
    }

}
