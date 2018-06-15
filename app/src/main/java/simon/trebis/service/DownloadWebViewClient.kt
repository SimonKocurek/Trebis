package simon.trebis.service

import android.content.ContentValues
import android.graphics.Bitmap
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import simon.trebis.Const.Companion.JAVASCRIPT_APP
import simon.trebis.data.DatabaseManager
import java.io.ByteArrayOutputStream
import java.util.concurrent.TimeUnit

class DownloadWebViewClient(
        private val websiteId: Long,
        private val databaseManager: DatabaseManager,
        private val downloadServiceHandler: DownloadServiceHandler,
        private val webView: WebView
) : WebViewClient() {

    // Prevents crashes
    override fun onReceivedError(webView: WebView, errorCode: Int, description: String, failingUrl: String) {
        Log.e(ContentValues.TAG, "Received error from WebView, description: $description, Failing url: $failingUrl")
    }

    override fun onPageFinished(webView: WebView, url: String) {
        webView.loadUrl("javascript:$JAVASCRIPT_APP.resize(document.body.getBoundingClientRect().height)")
        takeWebViewScreenshot(webView)

        super.onPageFinished(webView, url)
    }

    private fun takeWebViewScreenshot(webView: WebView) {
        try {
            // allow webView to render, otherwise screenshot may be blank or partial
            TimeUnit.MILLISECONDS.sleep(1000)

            val bitmap = webView.drawingCache
            val byteStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteStream)

            databaseManager.createEntry(websiteId, byteStream.toByteArray())
            downloadServiceHandler.stopService()

//            TimeUnit.MILLISECONDS.sleep(10000)

        } catch (e: InterruptedException) {
            Log.e(ContentValues.TAG, "InterruptedException when taking webView screenshot", e)
        }
    }

    @JavascriptInterface
    fun resize(height: Float) {
        downloadServiceHandler.dimensions()?.let {
            webView.measure(it.widthPixels, (it.density * height).toInt())
            webView.layout(0, 0, it.widthPixels, (it.density * height).toInt())
        }
    }

}
