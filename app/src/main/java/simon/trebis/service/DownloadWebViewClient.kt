package simon.trebis.service

import android.content.ContentValues
import android.content.Context
import android.text.format.DateFormat
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import java.util.*
import java.util.concurrent.TimeUnit

class DownloadWebViewClient(private val id: Int, private val context: Context) : WebViewClient() {

    // Prevents crashes
    override fun onReceivedError(webView: WebView, errorCode: Int, description: String, failingUrl: String) {
        Log.e(ContentValues.TAG, "Received error from WebView, description: $description, Failing url: $failingUrl")
    }

    override fun onPageFinished(webView: WebView, url: String) {
        val creationDate = DateFormat.format("yyyy-MM-dd HH:mm:ss", Date())
        takeWebViewScreenshot("$id/$creationDate", context, webView)
    }

    private fun takeWebViewScreenshot(fileName: String, context: Context, webView: WebView) {
        try {
            // allow webView to render, otherwise screenshot may be blank or partial
            TimeUnit.MILLISECONDS.sleep(5000)
            DiskUtils.saveBitmapToFile(webView.drawingCache, fileName, context)

        } catch (e: InterruptedException) {
            Log.e(ContentValues.TAG, "InterruptedException when taking webView screenshot", e)
        }
    }

}
