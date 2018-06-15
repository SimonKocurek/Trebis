package simon.trebis.service

import android.content.ContentValues
import android.graphics.Bitmap
import android.util.Log
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
        const val javascriptCallback = "javascript:$JAVASCRIPT_APP.resizeAndCapture(document.body.getBoundingClientRect().height)"
    }

    @JavascriptInterface
    fun resizeAndCapture(height: Float) {
        downloadServiceHandler.dimensions()?.let {
            webView.measure(it.widthPixels, (it.density * height).toInt())
            webView.layout(0, 0, it.widthPixels, (it.density * height).toInt())
        }

        takeWebViewScreenshot(webView)
    }

    private fun takeWebViewScreenshot(webView: WebView) {
        Thread {
            try {
                takeScreenshot(webView)
            } catch (e: InterruptedException) {
                Log.e(ContentValues.TAG, "InterruptedException when taking webView screenshot", e)
            }
        }.start()
    }

    private fun takeScreenshot(webView: WebView) {
        while (webView.drawingCache == null) {
            Thread.sleep(1000)
        }
        val bitmap = webView.drawingCache
        val byteStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteStream)

        databaseManager.createEntry(websiteId, byteStream.toByteArray())
        downloadServiceHandler.stopService()
    }

}
