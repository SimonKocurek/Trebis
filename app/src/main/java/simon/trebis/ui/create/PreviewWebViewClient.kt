package simon.trebis.ui.create

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast


class PreviewWebViewClient(val context: Context, private val onPageTitleLoaded: (String) -> Unit) :
        WebViewClient() {

    override fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String) {
        Toast.makeText(context, description, Toast.LENGTH_SHORT).show()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceivedError(view: WebView, req: WebResourceRequest, rerr: WebResourceError) {
        onReceivedError(view, rerr.errorCode, rerr.description.toString(), req.url.toString())
    }

    override fun onPageFinished(view: WebView, url: String) {
        view.title?.let {
            onPageTitleLoaded(it)
        }
    }

}
