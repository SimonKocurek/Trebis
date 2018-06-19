package simon.trebis.ui.create

import android.annotation.TargetApi
import android.os.Build
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient


class PreviewWebViewClient(private val onPageTitleLoaded: (String) -> Unit) :
        WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        view?.loadUrl(url)
        return false
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
        view.loadUrl(request.url.toString())
        return false
    }

    override fun onPageFinished(view: WebView, url: String) {
        view.title?.let {
            onPageTitleLoaded(it)
        }
    }

}
