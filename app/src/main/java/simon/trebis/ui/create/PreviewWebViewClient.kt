package simon.trebis.ui.create

import android.content.Context
import android.webkit.WebView
import android.webkit.WebViewClient


class PreviewWebViewClient(val context: Context, private val onPageTitleLoaded: (String) -> Unit) :
        WebViewClient() {

    override fun onPageFinished(view: WebView, url: String) {
        view.title?.let {
            onPageTitleLoaded(it)
        }
    }

}
