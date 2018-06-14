package simon.trebis.service

import android.annotation.SuppressLint
import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.os.Build
import android.webkit.WebView
import android.widget.Toast
import simon.trebis.Const.Companion.ACTION_FETCH_WEBSITE
import simon.trebis.Const.Companion.WEBSITE_ID
import simon.trebis.Const.Companion.WEBSITE_URL
import simon.trebis.data.DatabaseManager

class DownloadService : IntentService("DownloadService") {

    companion object {

        @JvmStatic
        fun startFetchAction(context: Context, url: String, websiteId: Int) {
            val intent = Intent(context, DownloadService::class.java).apply {
                action = ACTION_FETCH_WEBSITE
                putExtra(WEBSITE_URL, url)
                putExtra(WEBSITE_ID, websiteId)
            }

            context.startService(intent)
        }

    }

    override fun onHandleIntent(intent: Intent?) {
        when (intent?.action) {
            ACTION_FETCH_WEBSITE -> {
                val url = intent.getStringExtra(WEBSITE_URL)
                val websiteId = intent.getIntExtra(WEBSITE_ID, -1)
                handleFetchAction(url, websiteId)
            }
        }
    }

    private fun handleFetchAction(url: String, websiteId: Int) {
        val databaseManager = DatabaseManager.instance(applicationContext)

        configuredWebView(1280, 720).apply {
            webViewClient = DownloadWebViewClient(websiteId, databaseManager)
            loadUrl(url)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun configuredWebView(width: Int, height: Int): WebView {
        val webView = WebView(this@DownloadService)

        // without this toast message, screenshot will be blank, dont ask me why...
        Toast.makeText(this@DownloadService, "Picture taken.", Toast.LENGTH_SHORT).show()

        return webView.apply {
            // This is important, so that the webView will render and we don't get blank screenshot
            isDrawingCacheEnabled = true

            // width and height of your webView and the resulting screenshot
            measure(width, height)
            layout(0, 0, width, height)

            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true

            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                settings.allowFileAccessFromFileURLs = true
                settings.allowUniversalAccessFromFileURLs = true
            }
        }
    }

}
