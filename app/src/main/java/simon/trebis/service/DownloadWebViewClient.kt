package simon.trebis.service

import android.content.ContentValues
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import simon.trebis.service.JavascriptBridge.Companion.javascriptCallback

class DownloadWebViewClient : WebViewClient() {

    // Prevents crashes
    override fun onReceivedError(webView: WebView, errorCode: Int, description: String, failingUrl: String) {
        Log.e(ContentValues.TAG, "Received error from WebView, description: $description, Failing url: $failingUrl")
    }

    override fun onPageFinished(webView: WebView, url: String) {
        webView.loadUrl(javascriptCallback)
        super.onPageFinished(webView, url)
    }

}
