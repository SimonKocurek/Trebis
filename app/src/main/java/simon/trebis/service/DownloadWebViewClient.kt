package simon.trebis.service

import android.annotation.TargetApi
import android.content.ContentValues
import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import simon.trebis.service.JavascriptBridge.Companion.javascriptCallback


class DownloadWebViewClient : WebViewClient() {

    var expectedUrl: String? = ""

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        expectedUrl = url
    }

    // Prevents crashes
    override fun onReceivedError(webView: WebView, errorCode: Int, description: String, failingUrl: String) {
        Log.e(ContentValues.TAG, "Received error from WebView, description: $description, Failing url: $failingUrl")
    }

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        expectedUrl = url
        view?.loadUrl(expectedUrl)
        return false
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
        expectedUrl = request.url.toString()
        view.loadUrl(expectedUrl)
        return false
    }

    override fun onPageFinished(webView: WebView, url: String) {
        if (url == expectedUrl) {
            webView.loadUrl(javascriptCallback)
        }
    }

}
