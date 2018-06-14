package simon.trebis.service

import android.content.ContentValues
import android.graphics.Bitmap
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import simon.trebis.data.DatabaseManager
import java.io.ByteArrayOutputStream
import java.util.concurrent.TimeUnit

class DownloadWebViewClient(
        private val websiteId: Long,
        private val databaseManager: DatabaseManager,
        private val downloadServiceHandler: DownloadServiceHandler
) : WebViewClient() {

    // Prevents crashes
    override fun onReceivedError(webView: WebView, errorCode: Int, description: String, failingUrl: String) {
        Log.e(ContentValues.TAG, "Received error from WebView, description: $description, Failing url: $failingUrl")
    }

    override fun onPageFinished(webView: WebView, url: String) {
        takeWebViewScreenshot(webView)
    }

    private fun takeWebViewScreenshot(webView: WebView) {
        Thread {
            try {
                // allow webView to render, otherwise screenshot may be blank or partial
                TimeUnit.MILLISECONDS.sleep(5000)

                val bitmap = webView.drawingCache
                val byteStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteStream)

                databaseManager.createEntry(websiteId, byteStream.toByteArray())
                downloadServiceHandler.stopService()

            } catch (e: InterruptedException) {
                Log.e(ContentValues.TAG, "InterruptedException when taking webView screenshot", e)
            }
        }.start()
    }

}
