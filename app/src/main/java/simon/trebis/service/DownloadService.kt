package simon.trebis.service

import android.annotation.SuppressLint
import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.os.Build
import android.webkit.WebView
import android.widget.Toast

private const val ACTION_FETCH_WEBSITE = "simon.trebis.action.FETCH_WEBSITE"
private const val WEBSITE_URL = "simon.trebis.extra.WEBSITE_URL"

class DownloadService : IntentService("DownloadService") {

    companion object {

        @JvmStatic
        fun startFetchAction(context: Context, url: String) {
            val intent = Intent(context, DownloadService::class.java).apply {
                action = ACTION_FETCH_WEBSITE
                putExtra(WEBSITE_URL, url)
            }

            context.startService(intent)
        }

    }

    override fun onHandleIntent(intent: Intent?) {
        when (intent?.action) {
            ACTION_FETCH_WEBSITE -> {
                val url = intent.getStringExtra(WEBSITE_URL)
                handleFetchAction(url)
            }
        }
    }

    private fun handleFetchAction(url: String) {
        val webView = configuredWebView(1280, 720)

        webView.loadUrl(url)
        webView.webViewClient = DownloadWebViewClient()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun configuredWebView(width: Int, height: Int): WebView {
        val webView = WebView(this@DownloadService)

        // without this toast message, screenshot will be blank, dont ask me why...
        Toast.makeText(this@DownloadService, "Picture taken.", Toast.LENGTH_SHORT).show()

        // This is important, so that the webView will render and we don't get blank screenshot
        webView.isDrawingCacheEnabled = true

        //width and height of your webView and the resulting screenshot
        webView.measure(width, height)
        webView.layout(0, 0, width, height)

        webView.settings.javaScriptEnabled = true

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webView.settings.allowFileAccessFromFileURLs = true
            webView.settings.allowUniversalAccessFromFileURLs = true
        }

        return webView
    }

}
